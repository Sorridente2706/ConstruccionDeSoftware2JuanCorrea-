package com.bank.app.domain.service;

import com.bank.app.domain.model.entity.BankAccount;
import com.bank.app.domain.model.entity.Loan;
import com.bank.app.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoanDomainService {

    public void disburseLoanToAccount(Loan loan, BankAccount account) {
        if (!account.isActive())
            throw new ResourceNotFoundException("Disbursement account is not active: " + account.getAccountNumber());
        if (!account.getOwnerId().equals(loan.getClientId()))
            throw new IllegalArgumentException("Disbursement account does not belong to the loan client");
        loan.disburse(account.getAccountNumber());
        account.credit(loan.getApprovedAmount());
    }
}
