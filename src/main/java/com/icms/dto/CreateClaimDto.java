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
        name = "CreateClaimDto",
        description = "Schema to hold claim information"
)
public class CreateClaimDto {
    @Schema(
            description = "claim number in the response"
    )
    @NotBlank(message = "Claim number is required")
    @Size(min = 3, max = 50, message = "Claim number must be between 3 and 50 characters")
    private String claimNumber;
    @Schema(
            description = "claim date in the response"
    )
    @NotNull(message = "Claim date is required")
    @PastOrPresent(message = "Claim date cannot be in the future")
    private LocalDate claimDate;
    @Schema(
            description = "claim amount in the response"
    )
    @NotNull(message = "Claim amount is required")
    @Positive(message = "Claim amount must be greater than zero")
    private Long amountClaimed;
    @Schema(
            description = "claim status in the response"
    )
    @NotBlank(message = "Claim status is required")
    @Pattern(
            regexp = "OPEN|APPROVED|REJECTED",
            message = "Status must be one of: OPEN, APPROVED, REJECTED"
    )
    private String status; // OPEN, APPROVED, REJECTED
    @Schema(
            description = "policy id in the response"
    )
    @NotNull(message = "Policy ID is required")
    @Positive(message = "Policy ID must be a positive number")
    private Long policyId; // Link to existing policy
}
