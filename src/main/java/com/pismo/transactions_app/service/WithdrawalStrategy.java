package com.pismo.transactions_app.service;

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
public class WithdrawalStrategy implements IOperationStrategy {

    private static final Logger log = LoggerFactory.getLogger(WithdrawalStrategy.class);

    private static final BigDecimal NEGATIVE_SIGN = BigDecimal.valueOf(-1);

    @Override
    public Transaction process(final BigDecimal amount, final Account account) {
        log.debug("Processing WITHDRAWAL transaction. accountId={} amount={}", account.getId(), amount);
        final BigDecimal adjustedAmount = amount.multiply(NEGATIVE_SIGN);
        return new Transaction(account,
                OperationTypeEnum.WITHDRAWAL,
                adjustedAmount,
                LocalDateTime.now());
    }

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.WITHDRAWAL;
    }
}
