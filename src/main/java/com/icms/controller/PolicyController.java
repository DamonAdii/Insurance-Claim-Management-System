package com.icms.controller;

import com.icms.dto.ErrorResponseDto;
import com.icms.dto.PolicyCreateDto;
import com.icms.entity.Policy;
import com.icms.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Tag(
        name = "Policy CRUD operation in Insurance Claim Management System REST API",
        description = "Policy CRUD operation in Insurance Claim Management System REST API details"
)
@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @Operation(
            summary = "Create Policy REST API",
            description = "REST API to create policy inside ICMS"
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
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Policy> createPolicy(@RequestBody PolicyCreateDto dto) {
        log.info("Received request to create policy: {}", dto);
        Policy createdPolicy = policyService.createPolicy(dto);
        log.info("Policy created successfully with ID: {}", createdPolicy.getId());
        return ResponseEntity.ok(createdPolicy);
    }

    @Operation(
            summary = "Get Policy REST API",
            description = "REST API to get policy by policy id inside ICMS"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<Policy> getById(@PathVariable Long id) {
        log.info("Fetching policy by ID: {}", id);

        return policyService.getById(id)
                .map(policy -> {
                    log.info("Policy found with ID: {}", id);
                    return ResponseEntity.ok(policy);
                })
                .orElseGet(() -> {
                    log.warn("Policy not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

}
