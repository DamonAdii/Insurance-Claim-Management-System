package com.icms.controller;

import com.icms.dto.CreateClaimDto;
import com.icms.entity.Claim;
import com.icms.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Claim> createClaim(@RequestBody CreateClaimDto dto) {
        return ResponseEntity.ok(claimService.createClaim(dto));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<?> upload(@RequestParam Long claimId, @RequestParam("file") MultipartFile file) throws IOException {
        // validate file type, store file under configured baseDir + /claims/{claimId}/<filename>
        // update claim.filePath, save
        return ResponseEntity.ok(claimService.uploadFile(claimId, file));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<Claim> getById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Claim>> getAll() {
        return ResponseEntity.ok(claimService.getAll());
    }

}
