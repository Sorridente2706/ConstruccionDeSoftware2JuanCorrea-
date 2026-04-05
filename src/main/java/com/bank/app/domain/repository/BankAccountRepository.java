package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    BankAccount save(BankAccount account);
    Optional<BankAccount> findById(Long id);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
    List<BankAccount> findByOwnerId(String ownerId);
}
