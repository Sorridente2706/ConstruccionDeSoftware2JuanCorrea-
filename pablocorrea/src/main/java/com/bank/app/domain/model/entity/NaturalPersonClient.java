package com.bank.app.domain.model.entity;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class NaturalPersonClient {
    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;

    public void validateRequiredFields() {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name is required");
        if (identificationNumber == null || identificationNumber.isBlank()) throw new IllegalArgumentException("ID number is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (phone == null || phone.length() < 7 || phone.length() > 15) throw new IllegalArgumentException("Phone must be 7-15 digits");
        if (birthDate == null) throw new IllegalArgumentException("Birth date is required");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Address is required");
    }
}
