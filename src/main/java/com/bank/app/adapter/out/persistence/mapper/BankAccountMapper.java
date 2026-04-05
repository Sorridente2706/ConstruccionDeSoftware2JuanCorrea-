package com.bank.app.adapter.out.persistence.mapper;

import com.bank.app.adapter.out.persistence.entity.BankAccountJpaEntity;
import com.bank.app.domain.model.entity.BankAccount;
import com.bank.app.domain.model.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    public BankAccount toDomain(BankAccountJpaEntity e) {
        if (e == null) return null;
        return BankAccount.builder()
                .id(e.getId()).accountNumber(e.getAccountNumber())
                .accountType(e.getAccountType()).ownerId(e.getOwnerId())
                .balance(Money.of(e.getBalance(), e.getCurrency()))
                .status(e.getStatus()).openingDate(e.getOpeningDate())
                .build();
    }
    public BankAccountJpaEntity toJpa(BankAccount d) {
        if (d == null) return null;
        return BankAccountJpaEntity.builder()
                .id(d.getId()).accountNumber(d.getAccountNumber())
                .accountType(d.getAccountType()).ownerId(d.getOwnerId())
                .balance(d.getBalance().getAmount()).currency(d.getBalance().getCurrency())
                .status(d.getStatus()).openingDate(d.getOpeningDate())
                .build();
    }
}
