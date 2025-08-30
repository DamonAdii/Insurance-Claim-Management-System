package com.icms.service.Impl;

import com.icms.config.ClaimsFileConfig;
import com.icms.dto.CreateClaimDto;
import com.icms.entity.Claim;
import com.icms.entity.Policy;
import com.icms.repository.ClaimRepository;
import com.icms.repository.PolicyRepository;
import com.icms.repository.UserRepository;
import com.icms.service.ClaimService;
import com.icms.service.KafkaLogProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final ClaimsFileConfig claimsFileConfig;
    private final UserRepository userRepository;
    private final KafkaLogProducer kafkaLogProducer;

    @Value("${app.kafka.log-topic}")
    private String topic;

    @Override
    public Claim createClaim(CreateClaimDto dto) {
        log.info("Creating claim with claimNumber={} for policyId={}", dto.getClaimNumber(), dto.getPolicyId());

        if (claimRepository.existsByClaimNumber(dto.getClaimNumber())) {
            log.warn("Claim creation failed - claimNumber={} already exists", dto.getClaimNumber());
            throw new RuntimeException("Claim number already exists");
        }

        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> {
                    log.error("Policy not found for ID={}", dto.getPolicyId());
                    return new RuntimeException("Policy not found");
                });

        Claim claim = new Claim();
        claim.setClaimNumber(dto.getClaimNumber());
        claim.setClaimDate(dto.getClaimDate());
        claim.setAmountClaimed(dto.getAmountClaimed());
        claim.setStatus(dto.getStatus());
        claim.setPolicy(policy);

        Claim savedClaim = claimRepository.save(claim);
        log.info("Claim created successfully with ID={}", savedClaim.getId());

        return savedClaim;
    }

    @Override
    public Claim uploadFile(Long claimId, MultipartFile file) throws IOException {
        log.info("Uploading file for claimId={} with filename={}", claimId, file.getOriginalFilename());

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> {
                    log.error("Claim not found for ID={}", claimId);
                    return new RuntimeException("Claim not found");
                });

        // Get userId from logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("User not found with email={}", username);
                    return new RuntimeException("User not found");
                }).getId();

        // Create directory structure: baseDir/userId/claimId
        Path claimDir = Path.of(
                claimsFileConfig.getClaimsDir(),
                String.valueOf(userId),
                String.valueOf(claimId)
        );
        Files.createDirectories(claimDir);

        // Save file
        Path filePath = claimDir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        claim.setFilePath(filePath.toString());
        Claim updatedClaim = claimRepository.save(claim);

        log.info("File stored at {} for claimId={}", filePath, claimId);
        return updatedClaim;
    }
//    public Claim uploadFile(Long claimId, MultipartFile file) throws IOException {
//        Claim claim = claimRepository.findById(claimId)
//                .orElseThrow(() -> new RuntimeException("Claim not found"));
//
//        // Create directory for claim
//        Path claimDir = Path.of(claimsFileConfig.getClaimsDir(), String.valueOf(claimId));
//        Files.createDirectories(claimDir);
//
//        // Store file
//        Path filePath = claimDir.resolve(file.getOriginalFilename());
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        claim.setFilePath(filePath.toString());
//        return claimRepository.save(claim);
//    }

    @Override
    public Claim getById(Long id) {
        log.info("Fetching claim with ID={}", id);

        return claimRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Claim not found for ID={}", id);
                    return new RuntimeException("Claim not found");
                });
    }

    @Override
    public List<Claim> getAll() {
        log.info("Fetching all claims from repository");
        List<Claim> claims = claimRepository.findAll();
        log.info("Retrieved {} claims", claims.size());
        return claims;
    }


    @Transactional
    @Override
    public CompletableFuture<Claim> createClaimAsync(CreateClaimDto dto) {

        return CompletableFuture.supplyAsync(() -> {

//            log.info("Creating claim with claimNumber={} for policyId={}", dto.getClaimNumber(), dto.getPolicyId());
            kafkaLogProducer.sendLog(topic,"Creating claim with claimNumber "+dto.getClaimNumber());

            if (claimRepository.existsByClaimNumber(dto.getClaimNumber())) {
//                log.warn("Claim creation failed - claimNumber={} already exists", dto.getClaimNumber());
                kafkaLogProducer.sendLog(topic,"Claim creation failed - claimNumber="+dto.getClaimNumber()+" already exists");
                throw new RuntimeException("Claim number already exists");
            }

            Policy policy = policyRepository.findById(dto.getPolicyId())
                    .orElseThrow(() -> {
//                        log.error("Policy not found for ID={}", dto.getPolicyId());
                        kafkaLogProducer.sendLog(topic,"Policy not found for ID={}r="+dto.getPolicyId());
                        return new RuntimeException("Policy not found");
                    });

            Claim claim = new Claim();
            claim.setClaimNumber(dto.getClaimNumber());
            claim.setClaimDate(dto.getClaimDate());
            claim.setAmountClaimed(dto.getAmountClaimed());
            claim.setStatus(dto.getStatus());
            claim.setPolicy(policy);

            // save order in DB (transactional)
            Claim savedClaim = claimRepository.save(claim);
//            log.info("Claim created successfully with ID={}", savedClaim.getId());
            // publish log to Kafka asynchronously
//            sendLogAsync("Order placed: " + saved.getId());
//            kafkaLogProducer.sendLog("Claim created successfully with ID={}", String.valueOf(savedClaim.getId()));
            kafkaLogProducer.sendLog(topic,"Claim created successfully with ID="+savedClaim.getId());
            return savedClaim;
        });
    }

}
