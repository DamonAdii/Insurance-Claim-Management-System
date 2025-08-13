package com.icms.controller;

import com.icms.dto.ErrorResponseDto;
import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;
import com.icms.service.PolicyHolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Tag(
        name = "Policy Holder CRUD operation in Insurance Claim Management System REST API",
        description = "Policy Holder CRUD operation in Insurance Claim Management System REST API details"
)
@RestController
@RequestMapping("/api/policyholders")
@RequiredArgsConstructor
public class PolicyHolderController {

    private final PolicyHolderService policyHolderService;

    @Operation(
            summary = "Create Policy Holder REST API",
            description = "REST API to create policy holder inside ICMS"
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
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> create(@Valid @RequestBody PolicyHolderDto dto) {
        log.info("Received request to create policy holder: {}", dto);
        PolicyHolder created = policyHolderService.createPolicyHolder(dto);
        log.info("Policy holder created successfully with ID: {}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Policy Holder REST API",
            description = "REST API to get policy holder by policy holder id inside ICMS"
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> getById(@PathVariable Long id) {
        log.info("Fetching policy holder by ID: {}", id);

        PolicyHolder found = policyHolderService.getPolicyHolderById(id);

        log.info("Policy holder found with ID: {}", id);
        return ResponseEntity.ok(found);
    }

}
