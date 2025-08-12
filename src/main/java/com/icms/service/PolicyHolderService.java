package com.icms.service;

import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;

public interface PolicyHolderService {
    public PolicyHolder createPolicyHolder(PolicyHolderDto dto);
    public PolicyHolder getPolicyHolderById(Long id);
}
