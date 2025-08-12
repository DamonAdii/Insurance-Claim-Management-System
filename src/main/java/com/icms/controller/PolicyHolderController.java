package com.icms.controller;

import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;
import com.icms.service.PolicyHolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policyholders")
@RequiredArgsConstructor
public class PolicyHolderController {

    private final PolicyHolderService policyHolderService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> create(@Valid @RequestBody PolicyHolderDto dto) {
        PolicyHolder created = policyHolderService.createPolicyHolder(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> getById(@PathVariable Long id) {
        PolicyHolder found = policyHolderService.getPolicyHolderById(id);
        return ResponseEntity.ok(found);
    }

}
