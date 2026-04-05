package com.bank.app.domain.model.entity;

import com.bank.app.domain.exception.InvalidTransferStateException;
import com.bank.app.domain.model.valueobject.Money;
import com.bank.app.domain.model.valueobject.TransferStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Transfer {
    private Long id;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Money amount;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private TransferStatus status;
    private Long creatorUserId;
    private Long approverUserId;

    public void execute(Long approverId) {
        if (this.status != TransferStatus.PENDING_APPROVAL)
            throw new InvalidTransferStateException("Transfer can only be executed from PENDING_APPROVAL. Current: " + status);
        this.status = TransferStatus.EXECUTED;
        this.approverUserId = approverId;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(Long approverId) {
        if (this.status != TransferStatus.PENDING_APPROVAL)
            throw new InvalidTransferStateException("Transfer can only be rejected from PENDING_APPROVAL. Current: " + status);
        this.status = TransferStatus.REJECTED;
        this.approverUserId = approverId;
        this.approvedAt = LocalDateTime.now();
    }

    public void expire() {
        if (this.status != TransferStatus.PENDING_APPROVAL)
            throw new InvalidTransferStateException("Transfer can only expire from PENDING_APPROVAL. Current: " + status);
        this.status = TransferStatus.EXPIRED;
    }

    public boolean isExpired(int expirationMinutes) {
        return this.status == TransferStatus.PENDING_APPROVAL &&
               LocalDateTime.now().isAfter(this.createdAt.plusMinutes(expirationMinutes));
    }
}
