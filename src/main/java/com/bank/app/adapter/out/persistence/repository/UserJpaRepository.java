package com.bank.app.adapter.out.persistence.repository;

import com.bank.app.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByIdentificationNumber(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);
    boolean existsByUsername(String username);
}
