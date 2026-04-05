package com.bank.app.adapter.out.persistence.mapper;

import com.bank.app.adapter.out.persistence.entity.CompanyClientJpaEntity;
import com.bank.app.domain.model.entity.CompanyClient;
import org.springframework.stereotype.Component;

@Component
public class CompanyClientMapper {
    public CompanyClient toDomain(CompanyClientJpaEntity e) {
        if (e == null) return null;
        return CompanyClient.builder()
                .id(e.getId()).legalName(e.getLegalName()).taxId(e.getTaxId())
                .email(e.getEmail()).phone(e.getPhone()).address(e.getAddress())
                .legalRepresentativeId(e.getLegalRepresentativeId())
                .build();
    }
    public CompanyClientJpaEntity toJpa(CompanyClient d) {
        if (d == null) return null;
        return CompanyClientJpaEntity.builder()
                .id(d.getId()).legalName(d.getLegalName()).taxId(d.getTaxId())
                .email(d.getEmail()).phone(d.getPhone()).address(d.getAddress())
                .legalRepresentativeId(d.getLegalRepresentativeId())
                .build();
    }
}
