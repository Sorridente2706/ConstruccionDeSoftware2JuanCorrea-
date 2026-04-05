package com.bank.app.config;

import com.bank.app.application.port.input.TransferInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Scheduled job that automatically expires pending transfers older than 60 minutes.
 * Runs every 5 minutes.
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final TransferInputPort transferInputPort;

    @Scheduled(fixedDelay = 300000) // Every 5 minutes
    public void expireOldPendingTransfers() {
        log.info("[SCHEDULER] Checking for expired pending transfers...");
        transferInputPort.processExpiredTransfers();
    }
}
