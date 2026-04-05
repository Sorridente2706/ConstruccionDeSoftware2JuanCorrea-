package com.bank.app.domain.model.entity;

import lombok.*;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CompanyClient {
    private Long id;
    private String legalName;
    private String taxId;
    private String email;
    private String phone;
    private String address;
    private String legalRepresentativeId;

    public void validateRequiredFields() {
        if (legalName == null || legalName.isBlank()) throw new IllegalArgumentException("Legal name is required");
        if (taxId == null || taxId.isBlank()) throw new IllegalArgumentException("Tax ID (NIT) is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (phone == null || phone.length() < 7 || phone.length() > 15) throw new IllegalArgumentException("Phone must be 7-15 digits");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Address is required");
        if (legalRepresentativeId == null || legalRepresentativeId.isBlank()) throw new IllegalArgumentException("Legal representative is required");
    }
}
