package com.icms.controller;

import com.icms.dto.PolicyHolderDto;
import com.icms.entity.PolicyHolder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policyholders")
public class PolicyHolderController {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> create(@Valid @RequestBody PolicyHolderDto dto) {

        return null;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyHolder> getById(@PathVariable Long id) {

        return null;
    }

}
