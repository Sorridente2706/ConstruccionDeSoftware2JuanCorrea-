package com.bank.app.adapter.out.persistence.mapper;

import com.bank.app.adapter.out.persistence.entity.NaturalPersonJpaEntity;
import com.bank.app.domain.model.entity.NaturalPersonClient;
import org.springframework.stereotype.Component;

@Component
public class NaturalPersonMapper {
    public NaturalPersonClient toDomain(NaturalPersonJpaEntity e) {
        if (e == null) return null;
        return NaturalPersonClient.builder()
                .id(e.getId()).fullName(e.getFullName())
                .identificationNumber(e.getIdentificationNumber())
                .email(e.getEmail()).phone(e.getPhone())
                .birthDate(e.getBirthDate()).address(e.getAddress())
                .build();
    }
    public NaturalPersonJpaEntity toJpa(NaturalPersonClient d) {
        if (d == null) return null;
        return NaturalPersonJpaEntity.builder()
                .id(d.getId()).fullName(d.getFullName())
                .identificationNumber(d.getIdentificationNumber())
                .email(d.getEmail()).phone(d.getPhone())
                .birthDate(d.getBirthDate()).address(d.getAddress())
                .build();
    }
}
