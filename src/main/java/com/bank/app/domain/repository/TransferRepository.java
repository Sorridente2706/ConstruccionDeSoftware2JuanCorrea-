package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.Transfer;
import com.bank.app.domain.model.valueobject.TransferStatus;
import java.util.List;
import java.util.Optional;

public interface TransferRepository {
    Transfer save(Transfer transfer);
    Optional<Transfer> findById(Long id);
    List<Transfer> findByStatus(TransferStatus status);
    List<Transfer> findByCreatorUserId(Long userId);
    List<Transfer> findBySourceAccountNumber(String accountNumber);
    List<Transfer> findPendingExpired(int expirationMinutes);
}
