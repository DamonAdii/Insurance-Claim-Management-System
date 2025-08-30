package com.icms.service;

public interface LoggingService {
    public void logInfo(String message);

    public void logError(String message, Throwable ex);
}
