package com.pismo.transactions_app.domain.repository.interfaces;

import com.pismo.transactions_app.domain.entities.TransactionEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    default TransactionEntity save(final TransactionEntity transactionEntity) {
        return saveAll(Collections.singletonList(transactionEntity)).stream().findFirst().orElse(null);
    }

    List<TransactionEntity> saveAll(final List<TransactionEntity> transactionEntities);

    Optional<TransactionEntity> findById(final long id);

    List<TransactionEntity> findDebitOperationsByAccountId(final long accountId);

}
