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
        name = "PolicyHolderDto",
        description = "Schema to hold policy holder information"
)
public class PolicyHolderDto {
    @Schema(
            description = "policy holder name in the response"
    )
    private String name;
    @Schema(
            description = "policy holder email in the response"
    )
    private String email;
    @Schema(
            description = "policy holder dob in the response"
    )
    private LocalDate dob;
    @Schema(
            description = "policy holder phone in the response"
    )
    private String phone;

}
