package com.icms.repository;

import com.icms.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {
    boolean existsByPolicyNumber(String policyNumber);
}
