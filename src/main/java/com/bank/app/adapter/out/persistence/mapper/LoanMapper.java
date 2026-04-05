package com.bank.app.adapter.out.persistence.mapper;

import com.bank.app.adapter.out.persistence.entity.LoanJpaEntity;
import com.bank.app.domain.model.entity.Loan;
import com.bank.app.domain.model.valueobject.Money;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    public Loan toDomain(LoanJpaEntity e) {
        if (e == null) return null;
        String currency = e.getCurrency() != null ? e.getCurrency() : "USD";
        return Loan.builder()
                .id(e.getId()).loanType(e.getLoanType()).clientId(e.getClientId())
                .requestedAmount(e.getRequestedAmount() != null ? Money.of(e.getRequestedAmount(), currency) : null)
                .approvedAmount(e.getApprovedAmount() != null ? Money.of(e.getApprovedAmount(), currency) : null)
                .interestRate(e.getInterestRate()).termMonths(e.getTermMonths())
                .status(e.getStatus()).approvalDate(e.getApprovalDate())
                .disbursementDate(e.getDisbursementDate())
                .disbursementAccountNumber(e.getDisbursementAccountNumber())
                .analystId(e.getAnalystId())
                .build();
    }
    public LoanJpaEntity toJpa(Loan d) {
        if (d == null) return null;
        return LoanJpaEntity.builder()
                .id(d.getId()).loanType(d.getLoanType()).clientId(d.getClientId())
                .requestedAmount(d.getRequestedAmount() != null ? d.getRequestedAmount().getAmount() : null)
                .approvedAmount(d.getApprovedAmount() != null ? d.getApprovedAmount().getAmount() : null)
                .currency(d.getRequestedAmount() != null ? d.getRequestedAmount().getCurrency() : null)
                .interestRate(d.getInterestRate()).termMonths(d.getTermMonths())
                .status(d.getStatus()).approvalDate(d.getApprovalDate())
                .disbursementDate(d.getDisbursementDate())
                .disbursementAccountNumber(d.getDisbursementAccountNumber())
                .analystId(d.getAnalystId())
                .build();
    }
}
