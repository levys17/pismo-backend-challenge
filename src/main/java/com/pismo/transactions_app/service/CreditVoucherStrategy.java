package com.pismo.transactions_app.service;

import com.pismo.transactions_app.api.transaction.v1.TransactionController;
import com.pismo.transactions_app.domain.Account;
import com.pismo.transactions_app.domain.Transaction;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import com.pismo.transactions_app.service.interfaces.IOperationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CreditVoucherStrategy implements IOperationStrategy {

    private static final Logger log = LoggerFactory.getLogger(CreditVoucherStrategy.class);

    @Override
    public Transaction process(final BigDecimal amount, final Account account) {
        log.debug("Processing CREDIT_VOUCHER transaction. accountId={} amount={}", account.getId(), amount);
        return new Transaction(account,
                OperationTypeEnum.CREDIT_VOUCHER,
                amount,
                LocalDateTime.now());
    }

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.CREDIT_VOUCHER;
    }
}
