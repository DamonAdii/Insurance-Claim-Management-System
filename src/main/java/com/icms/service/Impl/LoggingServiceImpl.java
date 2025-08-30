package com.icms.service.Impl;

import com.icms.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggingServiceImpl implements LoggingService {

    @Async("loggingExecutor")
    @Override
    public void logInfo(String message) {
        log.info("[Async-INFO] {}", message);
    }

    @Async("loggingExecutor")
    @Override
    public void logError(String message, Throwable ex) {
        log.error("[Async-ERROR] {} - {}", message, ex.getMessage(), ex);
    }

}
