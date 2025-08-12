package com.icms.repository;

import com.icms.entity.Policy;
import com.icms.entity.PolicyHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyHolderRepository extends JpaRepository<PolicyHolder,Long> {
    
}
