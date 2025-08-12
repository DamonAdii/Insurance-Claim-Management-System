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
        name = "CreateClaimDto",
        description = "Schema to hold claim information"
)
public class CreateClaimDto {
    @Schema(
            description = "claim number in the response"
    )
    private String claimNumber;
    @Schema(
            description = "claim date in the response"
    )
    private LocalDate claimDate;
    @Schema(
            description = "claim amount in the response"
    )
    private Long amountClaimed;
    @Schema(
            description = "claim status in the response"
    )
    private String status; // OPEN, APPROVED, REJECTED
    @Schema(
            description = "policy id in the response"
    )
    private Long policyId; // Link to existing policy
}
