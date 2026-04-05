package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.Loan;
import com.bank.app.domain.model.valueobject.LoanStatus;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findByClientId(String clientId);
    List<Loan> findByStatus(LoanStatus status);
}
