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
        name = "PolicyHolderDto",
        description = "Schema to hold policy holder information"
)
public class PolicyHolderDto {
    @Schema(
            description = "policy holder name in the response"
    )
    @NotBlank(message = "Policy Holder Name is required")
    @Size(min = 2, max = 50, message = "Policy Holder Name must be between 2 and 50 characters")
    private String name;
    @Schema(
            description = "policy holder email in the response"
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    @Schema(
            description = "policy holder dob in the response"
    )
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;
    @Schema(
            description = "policy holder phone in the response"
    )
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

}
