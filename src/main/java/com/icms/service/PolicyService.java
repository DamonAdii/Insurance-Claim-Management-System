package com.icms.service;

import com.icms.dto.PolicyCreateDto;
import com.icms.entity.Policy;

import java.util.Optional;

public interface PolicyService {
    Policy createPolicy(PolicyCreateDto dto);
    Optional<Policy> getById(Long id);
}
