package com.icms.service.Impl;

import com.icms.dto.PolicyCreateDto;
import com.icms.entity.Policy;
import com.icms.entity.PolicyHolder;
import com.icms.repository.PolicyHolderRepository;
import com.icms.repository.PolicyRepository;
import com.icms.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyHolderRepository policyHolderRepository;

    @Override
    public Policy createPolicy(PolicyCreateDto dto) {
        // Check if policy number is unique
        if (policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists: " + dto.getPolicyNumber());
        }

        // Find policy holder
        PolicyHolder holder = policyHolderRepository.findById(dto.getPolicyHolderId())
                .orElseThrow(() -> new RuntimeException("Policy Holder not found with ID: " + dto.getPolicyHolderId()));

        // Map DTO to Entity
        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setType(dto.getType());
        policy.setCoverageAmount(dto.getCoverageAmount());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPolicyHolder(holder);

        return policyRepository.save(policy);
    }

    @Override
    public Optional<Policy> getById(Long id) {
        return policyRepository.findById(id);
    }
}
