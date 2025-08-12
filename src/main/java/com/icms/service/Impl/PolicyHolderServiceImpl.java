package com.icms.service.Impl;

import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;
import com.icms.exception.ResourceNotFoundException;
import com.icms.repository.PolicyHolderRepository;
import com.icms.service.PolicyHolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyHolderServiceImpl implements PolicyHolderService {

    private final PolicyHolderRepository policyHolderRepository;

    @Override
    public PolicyHolder createPolicyHolder(PolicyHolderDto dto) {
        // Map DTO to entity
        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setName(dto.getName());
        policyHolder.setEmail(dto.getEmail());
        policyHolder.setDob(dto.getDob());
        policyHolder.setPhone(dto.getPhone());

        return policyHolderRepository.save(policyHolder);
    }

    @Override
    public PolicyHolder getPolicyHolderById(Long id) {
        return policyHolderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyHolder not found with id: " + id));
    }
}
