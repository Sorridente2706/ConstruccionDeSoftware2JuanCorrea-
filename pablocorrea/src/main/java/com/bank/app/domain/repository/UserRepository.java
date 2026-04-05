package com.bank.app.domain.repository;

import com.bank.app.domain.model.entity.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByIdentificationNumber(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);
    boolean existsByUsername(String username);
    List<User> findAll();
}
