package com.icms.controller;

import com.icms.dto.CreateClaimDto;
import com.icms.entity.Claim;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @PostMapping
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Claim> createClaim(@RequestBody CreateClaimDto dto) {

        return null;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<?> upload(@RequestParam Long claimId, @RequestParam("file") MultipartFile file) {
        // validate file type, store file under configured baseDir + /claims/{claimId}/<filename>
        // update claim.filePath, save
        return null;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<Claim> getById(@PathVariable Long id) {

        return null;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Claim>> getAll() {

        return null;
    }

}
