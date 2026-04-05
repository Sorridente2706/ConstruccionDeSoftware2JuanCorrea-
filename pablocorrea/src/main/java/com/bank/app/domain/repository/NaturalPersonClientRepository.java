package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.NaturalPersonClient;
import java.util.Optional;

public interface NaturalPersonClientRepository {
    NaturalPersonClient save(NaturalPersonClient client);
    Optional<NaturalPersonClient> findById(Long id);
    Optional<NaturalPersonClient> findByIdentificationNumber(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);
}
