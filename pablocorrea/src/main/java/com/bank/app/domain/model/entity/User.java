package com.bank.app.domain.model.entity;

import com.bank.app.domain.exception.UserNotActiveException;
import com.bank.app.domain.model.valueobject.UserRole;
import com.bank.app.domain.model.valueobject.UserStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    private Long id;
    private String relatedEntityId;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;
    private UserRole role;
    private UserStatus status;
    private String username;
    private String passwordHash;

    public void validateIsActive() {
        if (this.status != UserStatus.ACTIVE)
            throw new UserNotActiveException("User '" + username + "' is not active. Status: " + status);
    }
    public void validateIsAdult() {
        if (birthDate != null && Period.between(birthDate, LocalDate.now()).getYears() < 18)
            throw new IllegalStateException("User must be at least 18 years old.");
    }
    public boolean isActive() { return UserStatus.ACTIVE.equals(this.status); }
    public boolean hasRole(UserRole r) { return this.role == r; }
    public void activate()   { this.status = UserStatus.ACTIVE; }
    public void block()      { this.status = UserStatus.BLOCKED; }
    public void deactivate() { this.status = UserStatus.INACTIVE; }
}
