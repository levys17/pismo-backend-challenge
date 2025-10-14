package com.pismo.transactions_app.service.interfaces;

import com.pismo.transactions_app.domain.Account;
import com.pismo.transactions_app.domain.Transaction;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;

import java.math.BigDecimal;

public interface IOperationStrategy {

    Transaction process(final BigDecimal amount, final Account account);

    OperationTypeEnum getOperationType();
}
