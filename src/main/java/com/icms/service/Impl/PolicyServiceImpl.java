package com.icms.service.Impl;

import com.icms.dto.PolicyCreateDto;
import com.icms.entity.Policy;
import com.icms.entity.PolicyHolder;
import com.icms.repository.PolicyHolderRepository;
import com.icms.repository.PolicyRepository;
import com.icms.service.PolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyHolderRepository policyHolderRepository;

    @Override
    public Policy createPolicy(PolicyCreateDto dto) {
        log.debug("Checking if policy number {} already exists", dto.getPolicyNumber());
        // Check if policy number is unique
        if (policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            log.error("Policy number already exists: {}", dto.getPolicyNumber());
            throw new RuntimeException("Policy number already exists: " + dto.getPolicyNumber());
        }

        log.debug("Fetching policy holder with ID: {}", dto.getPolicyHolderId());
        // Find policy holder
        PolicyHolder holder = policyHolderRepository.findById(dto.getPolicyHolderId())
                .orElseThrow(() -> {
                    log.error("Policy Holder not found with ID: {}", dto.getPolicyHolderId());
                    return new RuntimeException("Policy Holder not found with ID: " + dto.getPolicyHolderId());
                });

        log.debug("Mapping PolicyCreateDto to Policy entity");
        // Map DTO to Entity
        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setType(dto.getType());
        policy.setCoverageAmount(dto.getCoverageAmount());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPolicyHolder(holder);

        Policy savedPolicy = policyRepository.save(policy);
        log.info("Policy created successfully with ID: {}", savedPolicy.getId());

        return savedPolicy;
    }

    @Override
    public Optional<Policy> getById(Long id) {
        log.debug("Fetching policy with ID: {}", id);
        Optional<Policy> policy = policyRepository.findById(id);

        if (policy.isPresent()) {
            log.info("Policy found with ID: {}", id);
        } else {
            log.warn("No policy found with ID: {}", id);
        }

        return policy;
    }
}
