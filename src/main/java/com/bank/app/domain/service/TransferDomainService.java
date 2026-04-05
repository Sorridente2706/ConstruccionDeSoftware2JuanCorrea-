package com.bank.app.domain.service;

import com.bank.app.domain.model.entity.BankAccount;
import com.bank.app.domain.model.entity.Transfer;
import com.bank.app.domain.model.valueobject.Money;
import com.bank.app.domain.model.valueobject.TransferStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransferDomainService {

    @Value("${app.transfer.approval.threshold:10000.00}")
    private BigDecimal approvalThreshold;

    public boolean requiresApproval(Money amount) {
        return amount.getAmount().compareTo(approvalThreshold) > 0;
    }

    public TransferStatus determineInitialStatus(Money amount) {
        return requiresApproval(amount) ? TransferStatus.PENDING_APPROVAL : TransferStatus.EXECUTED;
    }

    public void executeTransfer(BankAccount source, BankAccount destination, Money amount) {
        source.debit(amount);
        destination.credit(amount);
    }
}
