package com.icms.service;

import com.icms.dto.CreateClaimDto;
import com.icms.entity.Claim;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ClaimService {
    public Claim createClaim(CreateClaimDto dto);
    public Claim uploadFile(Long claimId, MultipartFile file) throws IOException;
    public Claim getById(Long id);
    public List<Claim> getAll();
    public CompletableFuture<Claim> createClaimAsync(CreateClaimDto dto);
}
