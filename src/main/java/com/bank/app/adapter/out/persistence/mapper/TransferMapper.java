package com.bank.app.adapter.out.persistence.mapper;

import com.bank.app.adapter.out.persistence.entity.TransferJpaEntity;
import com.bank.app.domain.model.entity.Transfer;
import com.bank.app.domain.model.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {
    public Transfer toDomain(TransferJpaEntity e) {
        if (e == null) return null;
        return Transfer.builder()
                .id(e.getId())
                .sourceAccountNumber(e.getSourceAccountNumber())
                .destinationAccountNumber(e.getDestinationAccountNumber())
                .amount(Money.of(e.getAmount(), e.getCurrency()))
                .createdAt(e.getCreatedAt()).approvedAt(e.getApprovedAt())
                .status(e.getStatus())
                .creatorUserId(e.getCreatorUserId())
                .approverUserId(e.getApproverUserId())
                .build();
    }
    public TransferJpaEntity toJpa(Transfer d) {
        if (d == null) return null;
        return TransferJpaEntity.builder()
                .id(d.getId())
                .sourceAccountNumber(d.getSourceAccountNumber())
                .destinationAccountNumber(d.getDestinationAccountNumber())
                .amount(d.getAmount().getAmount()).currency(d.getAmount().getCurrency())
                .createdAt(d.getCreatedAt()).approvedAt(d.getApprovedAt())
                .status(d.getStatus())
                .creatorUserId(d.getCreatorUserId())
                .approverUserId(d.getApproverUserId())
                .build();
    }
}
