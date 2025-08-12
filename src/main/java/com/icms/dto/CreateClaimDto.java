package com.icms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClaimDto {
    private String claimNumber;
    private LocalDate claimDate;
    private Long amountClaimed;
    private String status; // OPEN, APPROVED, REJECTED
    private Long policyId; // Link to existing policy
}
