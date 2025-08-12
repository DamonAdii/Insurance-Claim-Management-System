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
public class PolicyCreateDto {
    private String policyNumber;
    private String type;
    private long coverageAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private long policyHolderId;
}
