package com.flowform.FlowForm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration class to set up asynchronous processing with a custom thread pool.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Defines a thread pool executor named "emailTaskExecutor" with a maximum of 5 concurrent threads.
     *
     * @return Configured Executor
     */
    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Number of concurrent threads
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("EmailValidator-");
        executor.initialize();
        return executor;
    }
}
