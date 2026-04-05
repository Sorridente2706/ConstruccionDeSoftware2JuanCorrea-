package com.bank.app.domain.model.entity;

import com.bank.app.domain.exception.InvalidLoanStateTransitionException;
import com.bank.app.domain.model.valueobject.LoanStatus;
import com.bank.app.domain.model.valueobject.Money;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Loan {
    private Long id;
    private String loanType;
    private String clientId;
    private Money requestedAmount;
    private Money approvedAmount;
    private BigDecimal interestRate;
    private Integer termMonths;
    private LoanStatus status;
    private LocalDate approvalDate;
    private LocalDate disbursementDate;
    private String disbursementAccountNumber;
    private Long analystId;

    public void approve(Long analystId, Money approvedAmount, BigDecimal interestRate) {
        if (this.status != LoanStatus.UNDER_REVIEW)
            throw new InvalidLoanStateTransitionException("Loan can only be approved from UNDER_REVIEW. Current: " + status);
        this.status = LoanStatus.APPROVED;
        this.analystId = analystId;
        this.approvedAmount = approvedAmount;
        this.interestRate = interestRate;
        this.approvalDate = LocalDate.now();
    }

    public void reject(Long analystId) {
        if (this.status != LoanStatus.UNDER_REVIEW)
            throw new InvalidLoanStateTransitionException("Loan can only be rejected from UNDER_REVIEW. Current: " + status);
        this.status = LoanStatus.REJECTED;
        this.analystId = analystId;
    }

    public void disburse(String accountNumber) {
        if (this.status != LoanStatus.APPROVED)
            throw new InvalidLoanStateTransitionException("Loan can only be disbursed from APPROVED. Current: " + status);
        if (accountNumber == null || accountNumber.isBlank())
            throw new IllegalArgumentException("Disbursement account number is required");
        if (approvedAmount == null || !approvedAmount.isPositive())
            throw new IllegalArgumentException("Approved amount must be greater than zero");
        this.status = LoanStatus.DISBURSED;
        this.disbursementAccountNumber = accountNumber;
        this.disbursementDate = LocalDate.now();
    }
}
