package com.pismo.transactions_app.domain.repository;

import com.pismo.transactions_app.domain.entities.TransactionEntity;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionJpaRepository;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepository implements ITransactionRepository {

    private final ITransactionJpaRepository transactionJpaRepository;

    @Override
    public TransactionEntity save(final TransactionEntity transactionEntity) {
        return transactionJpaRepository.save(transactionEntity);
    }
}
