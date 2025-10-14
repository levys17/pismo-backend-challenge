package com.pismo.transactions_app.service;

import com.pismo.transactions_app.api.transaction.v1.request.TransactionRequestDto;
import com.pismo.transactions_app.api.transaction.v1.response.TransactionResponseDto;
import com.pismo.transactions_app.domain.Account;
import com.pismo.transactions_app.domain.Transaction;
import com.pismo.transactions_app.domain.entities.AccountEntity;
import com.pismo.transactions_app.domain.entities.TransactionEntity;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import com.pismo.transactions_app.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions_app.domain.repository.interfaces.IAccountRepository;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionRepository;
import com.pismo.transactions_app.service.interfaces.IOperationStrategy;
import com.pismo.transactions_app.service.interfaces.ITransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final OperationStrategyFactory strategyFactory;
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionResponseDto create(final TransactionRequestDto request) {
        final AccountEntity accountEntity = accountRepository.findById(request.getAccountId()).orElseThrow(
                () -> new AccountNotFoundException(request.getAccountId())
        );
        final OperationTypeEnum operationType = OperationTypeEnum.fromId(request.getOperationTypeId());
        final IOperationStrategy strategy = strategyFactory.getByOperationType(operationType);

        final Transaction transaction = strategy.process(request.getAmount(), Account.from(accountEntity));
        final TransactionEntity transactionEntity = transactionRepository.save(TransactionEntity.fromDomain(transaction));

        return TransactionResponseDto.from(transactionEntity);
    }
}
