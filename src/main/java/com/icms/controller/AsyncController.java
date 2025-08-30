package com.icms.controller;

import com.icms.dto.ErrorResponseDto;
import com.icms.entity.User;
import com.icms.repository.UserRepository;
import com.icms.service.LoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(
        name = "Async User CRUD operation in Insurance Claim Management System REST API",
        description = "Async User CRUD operation in Insurance Claim Management System REST API details"
)
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/async")
public class AsyncController {

    private final LoggingService loggingService;
    private final UserRepository userRepository;

    @Operation(
            summary = "Get All Users List Using Async",
            description = "REST API to get all async users list inside ICMS"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",

                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "402",
                    description = "HTTP Status UnAuthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<List<User>> getAllUsers(){
        long start = System.currentTimeMillis();
        loggingService.logInfo("Get request for async users");
        List<User> allUsersList = userRepository.findAll();
        loggingService.logInfo("Get all async users successfully");
        long end = System.currentTimeMillis();
        System.out.println("total time is : "+ (end-start));
        return ResponseEntity.ok(allUsersList);
    }


}
