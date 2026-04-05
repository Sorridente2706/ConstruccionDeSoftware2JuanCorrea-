package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.CompanyClient;
import java.util.Optional;

public interface CompanyClientRepository {
    CompanyClient save(CompanyClient company);
    Optional<CompanyClient> findById(Long id);
    Optional<CompanyClient> findByTaxId(String taxId);
    boolean existsByTaxId(String taxId);
}
