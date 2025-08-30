package com.icms.controller;

import com.icms.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/async")
public class AsyncController {

    private final LoggingService loggingService;



}
