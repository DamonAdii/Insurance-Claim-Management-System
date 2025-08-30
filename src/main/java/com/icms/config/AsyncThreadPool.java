package com.icms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncThreadPool {

    @Bean("loggingExecutor")
    public Executor loggingExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(2);
        exec.setMaxPoolSize(5);
        exec.setQueueCapacity(50);
        exec.setThreadNamePrefix("icms-log-thread-");
        exec.initialize();
        return exec;
    }

}
