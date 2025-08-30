package com.icms.controller;

import com.icms.dto.CreateClaimDto;
import com.icms.dto.ErrorResponseDto;
import com.icms.entity.Claim;
import com.icms.service.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/completeble")
public class CompletbleFutureAsyncController {

    private final ClaimService claimService;

    @Operation(
            summary = "Async completeble future Create Claim REST API",
            description = "REST API to create Async completeble future new claim inside ICMS"
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
    @PostMapping(value = "/claim/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody CreateClaimDto dto) throws Exception {
        long start = System.currentTimeMillis();
        Claim claim = claimService.createClaimAsync(dto).get();
        long end = System.currentTimeMillis();
        System.out.println("total time is : "+ (end - start)+" ms");
        return ResponseEntity.ok(claim);
    }

    @PostMapping(value = "/claim/get", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public String createget() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User={} Roles={}", auth.getName(), auth.getAuthorities());
        return "hi this is working";
    }


}
