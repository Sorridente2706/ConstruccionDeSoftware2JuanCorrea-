package com.bank.app.adapter.out.nosql;

import com.bank.app.application.dto.request.AuditLogRequest;
import com.bank.app.application.port.output.AuditLogOutputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Audit Log Adapter — NoSQL Output Port Implementation.
 *
 * In production: replace this with a MongoDB / DynamoDB / Elasticsearch adapter.
 * The domain and application layers are fully decoupled from this implementation.
 *
 * Each log entry represents an immutable audit record with:
 *  - operationType, operationDateTime, userId, userRole
 *  - affectedProductId, and a flexible details map (JSON document)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryAuditLogAdapter implements AuditLogOutputPort {

    private final ObjectMapper objectMapper;

    @Override
    public void log(AuditLogRequest logRequest) {
        try {
            String json = objectMapper.writeValueAsString(logRequest);
            log.info("[AUDIT-LOG] {}", json);
        } catch (Exception e) {
            log.error("[AUDIT-LOG] Failed to serialize audit log entry", e);
        }
    }
}
