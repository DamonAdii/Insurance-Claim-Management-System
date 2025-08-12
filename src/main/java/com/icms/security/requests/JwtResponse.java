package com.icms.security.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type;
    private long expiresAt;
}
