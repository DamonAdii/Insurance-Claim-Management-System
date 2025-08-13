package com.icms.service.Impl;

import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;
import com.icms.exception.ResourceNotFoundException;
import com.icms.repository.PolicyHolderRepository;
import com.icms.service.PolicyHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyHolderServiceImpl implements PolicyHolderService {

    private final PolicyHolderRepository policyHolderRepository;

    @Override
    public PolicyHolder createPolicyHolder(PolicyHolderDto dto) {
        log.debug("Mapping PolicyHolderDto to PolicyHolder entity: {}", dto);
        // Map DTO to entity
        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setName(dto.getName());
        policyHolder.setEmail(dto.getEmail());
        policyHolder.setDob(dto.getDob());
        policyHolder.setPhone(dto.getPhone());

        PolicyHolder savedHolder = policyHolderRepository.save(policyHolder);
        log.info("Policy holder created with ID: {}", savedHolder.getId());

        return savedHolder;
    }

    @Override
    public PolicyHolder getPolicyHolderById(Long id) {
        log.debug("Looking for policy holder with ID: {}", id);

        return policyHolderRepository.findById(id)
                .map(holder -> {
                    log.info("Policy holder found with ID: {}", id);
                    return holder;
                })
                .orElseThrow(() -> {
                    log.error("PolicyHolder not found with id: {}", id);
                    return new ResourceNotFoundException("PolicyHolder not found with id: " + id);
                });
    }
}
