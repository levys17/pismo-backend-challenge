package com.pismo.transactions_app.domain.repository.interfaces;

import com.pismo.transactions_app.domain.entities.TransactionEntity;

public interface ITransactionRepository {

    TransactionEntity save(final TransactionEntity transactionEntity);

}
