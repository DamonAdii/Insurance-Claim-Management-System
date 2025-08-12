package com.icms.service.Impl;

import com.icms.config.ClaimsFileConfig;
import com.icms.dto.CreateClaimDto;
import com.icms.entity.Claim;
import com.icms.entity.Policy;
import com.icms.repository.ClaimRepository;
import com.icms.repository.PolicyRepository;
import com.icms.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final ClaimsFileConfig claimsFileConfig;
    @Override
    public Claim createClaim(CreateClaimDto dto) {
        if (claimRepository.existsByClaimNumber(dto.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists");
        }

        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Claim claim = new Claim();
        claim.setClaimNumber(dto.getClaimNumber());
        claim.setClaimDate(dto.getClaimDate());
        claim.setAmountClaimed(dto.getAmountClaimed());
        claim.setStatus(dto.getStatus());
        claim.setPolicy(policy);

        return claimRepository.save(claim);
    }

    @Override
    public Claim uploadFile(Long claimId, MultipartFile file) throws IOException {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // Create directory for claim
        Path claimDir = Path.of(claimsFileConfig.getClaimsDir(), String.valueOf(claimId));
        Files.createDirectories(claimDir);

        // Store file
        Path filePath = claimDir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        claim.setFilePath(filePath.toString());
        return claimRepository.save(claim);
    }

    @Override
    public Claim getById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    @Override
    public List<Claim> getAll() {
        return claimRepository.findAll();
    }
}
