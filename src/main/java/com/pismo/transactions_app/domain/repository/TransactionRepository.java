package com.pismo.transactions_app.domain.repository;

import com.pismo.transactions_app.domain.entities.TransactionEntity;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionJpaRepository;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionRepository implements ITransactionRepository {

    private final ITransactionJpaRepository transactionJpaRepository;

    @Override
    public List<TransactionEntity> saveAll(final List<TransactionEntity> transactionEntityList) {
        return transactionJpaRepository.saveAll(transactionEntityList);
    }

    @Override
    public Optional<TransactionEntity> findById(long id) {
        return transactionJpaRepository.findById(id);
    }

    @Override
    public List<TransactionEntity> findDebitOperationsByAccountId(final long accountId) {
        return transactionJpaRepository.findDebitOperationsByAccountId(accountId);
    }
}
