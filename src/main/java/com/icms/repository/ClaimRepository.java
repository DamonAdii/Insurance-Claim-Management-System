package com.icms.repository;

import com.icms.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long> {
    Optional<Claim> findByClaimNumber(String claimNumber);
    boolean existsByClaimNumber(String claimNumber);
}
