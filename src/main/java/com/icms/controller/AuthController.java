package com.icms.controller;

import com.icms.entity.User;
import com.icms.repository.UserRepository;
import com.icms.security.JwtUtil;
import com.icms.security.requests.ApiResponse;
import com.icms.security.requests.JwtResponse;
import com.icms.security.requests.LoginRequest;
import com.icms.security.requests.RegisterRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email already exists"));
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        // expecting role string as "ADMIN" or "AGENT" from client
        u.setRole(req.getRole().toUpperCase());
        userRepository.save(u);
        return ResponseEntity.ok(new ApiResponse(true, "User registered"));
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return userRepository.findByEmail(req.getEmail())
                .map(user -> {
                    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                        return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid credentials"));
                    }
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
                    return ResponseEntity.ok(new JwtResponse(token, "Bearer", jwtUtil.extractExpiration(token).toInstant().toEpochMilli()));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(new ApiResponse(false, "Invalid credentials")));
    }

}
