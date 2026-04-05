package com.bank.app.adapter.out.persistence.repository;

import com.bank.app.adapter.out.persistence.entity.TransferJpaEntity;
import com.bank.app.domain.model.valueobject.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TransferJpaRepository extends JpaRepository<TransferJpaEntity, Long> {
    List<TransferJpaEntity> findByStatus(TransferStatus status);
    List<TransferJpaEntity> findByCreatorUserId(Long userId);
    List<TransferJpaEntity> findBySourceAccountNumber(String accountNumber);

    @Query("SELECT t FROM TransferJpaEntity t WHERE t.status = 'PENDING_APPROVAL' AND t.createdAt < :expirationTime")
    List<TransferJpaEntity> findExpiredPendingTransfers(@Param("expirationTime") LocalDateTime expirationTime);
}
