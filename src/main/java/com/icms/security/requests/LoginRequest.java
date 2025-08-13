package com.icms.security.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "User Login",
        description = "Schema to hold user login information"
)
public class LoginRequest {
    @Schema(
            description = "User email in the response"
    )
    private String email;
    @Schema(
            description = "User password in the response"
    )
    private String password;
}
