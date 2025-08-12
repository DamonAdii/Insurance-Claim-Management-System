package com.icms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "PolicyCreateDto",
        description = "Schema to hold policy information"
)
public class PolicyCreateDto {
    @Schema(
            description = "policy number in the response"
    )
    private String policyNumber;
    @Schema(
            description = "policy type in the response"
    )
    private String type;
    @Schema(
            description = "policy coverage amount in the response"
    )
    private long coverageAmount;
    @Schema(
            description = "policy start date in the response"
    )
    private LocalDate startDate;
    @Schema(
            description = "policy end date in the response"
    )
    private LocalDate endDate;
    @Schema(
            description = "policy holder id in the response"
    )
    private long policyHolderId;
}
