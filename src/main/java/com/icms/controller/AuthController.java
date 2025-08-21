package com.icms.controller;

import com.icms.dto.ErrorResponseDto;
import com.icms.entity.User;
import com.icms.repository.UserRepository;
import com.icms.security.JwtUtil;
import com.icms.security.requests.ApiResponse;
import com.icms.security.requests.JwtResponse;
import com.icms.security.requests.LoginRequest;
import com.icms.security.requests.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Tag(
        name = "Authentication and Authorization in Insurance Claim Management System REST API",
        description = "Authentication and Authorization in Insurance Claim Management System REST API details"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Operation(
            summary = "Register Users REST API",
            description = "REST API to create new user inside ICMS"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        log.info("Received registration request for email: {}", req.getEmail());

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            log.warn("Registration failed — email already exists: {}", req.getEmail());
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email already exists"));
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        // expecting role string as "ADMIN" or "AGENT" from client
        u.setRole(req.getRole().toUpperCase());
        userRepository.save(u);
        log.info("User registered successfully: {}", req.getEmail());
        return ResponseEntity.ok(new ApiResponse(true, "User registered"));
    }


    @Operation(
            summary = "Login User REST API",
            description = "REST API to login inside ICMS"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        log.info("Login attempt for email: {}", req.getEmail());

//        return userRepository.findByEmail(req.getEmail())
//                .map(user -> {
//                    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//                        log.warn("Login failed — invalid password for email: {}", req.getEmail());
//                        return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid credentials"));
//                    }
//                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
//                    log.info("Login successful for email: {}", req.getEmail());
//                    return ResponseEntity.ok(new JwtResponse(token, "Bearer", jwtUtil.extractExpiration(token).toInstant().toEpochMilli()));
//                })
//                .orElseGet(() -> {
//                    log.warn("Login failed — no user found with email: {}", req.getEmail());
//                    return ResponseEntity.status(401)
//                            .body(new ApiResponse(false, "Invalid credentials"));
//                });

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            // If we reach here → authentication succeeded
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(req.getEmail(),
                    authentication.getAuthorities().iterator().next().getAuthority());

            return ResponseEntity.ok(new JwtResponse(token, "Bearer",
                    jwtUtil.extractExpiration(token).toInstant().toEpochMilli()));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid credentials"));
        }

    }

}
