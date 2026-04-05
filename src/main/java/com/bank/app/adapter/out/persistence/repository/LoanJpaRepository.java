package com.bank.app.adapter.out.persistence.repository;

import com.bank.app.adapter.out.persistence.entity.LoanJpaEntity;
import com.bank.app.domain.model.valueobject.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanJpaRepository extends JpaRepository<LoanJpaEntity, Long> {
    List<LoanJpaEntity> findByClientId(String clientId);
    List<LoanJpaEntity> findByStatus(LoanStatus status);
}
