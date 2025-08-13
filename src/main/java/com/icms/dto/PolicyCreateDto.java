package com.icms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Policy number is required")
    @Size(min = 3, max = 50, message = "Policy number must be between 3 and 50 characters")
    private String policyNumber;
    @Schema(
            description = "policy type in the response"
    )
    @NotBlank(message = "Policy type is required")
    @Size(min = 3, max = 30, message = "Policy type must be between 3 and 30 characters")
    private String type;
    @Schema(
            description = "policy coverage amount in the response"
    )
    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage amount must be greater than zero")
    private long coverageAmount;
    @Schema(
            description = "policy start date in the response"
    )
    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;
    @Schema(
            description = "policy end date in the response"
    )
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    @Schema(
            description = "policy holder id in the response"
    )
    @NotNull(message = "Policy holder ID is required")
    @Positive(message = "Policy holder ID must be a positive number")
    private long policyHolderId;
}
