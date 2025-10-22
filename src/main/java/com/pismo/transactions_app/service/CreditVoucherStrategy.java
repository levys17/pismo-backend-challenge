package com.pismo.transactions_app.service;

import com.pismo.transactions_app.domain.Account;
import com.pismo.transactions_app.domain.Transaction;
import com.pismo.transactions_app.domain.entities.TransactionEntity;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import com.pismo.transactions_app.domain.repository.interfaces.ITransactionRepository;
import com.pismo.transactions_app.service.interfaces.IOperationStrategy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditVoucherStrategy implements IOperationStrategy {

    private static final Logger log = LoggerFactory.getLogger(CreditVoucherStrategy.class);
    private final ITransactionRepository transactionRepository;

    @Override
    public Transaction process(final BigDecimal amount, final Account account) {
        log.debug("Processing CREDIT_VOUCHER transaction. accountId={} amount={}", account.getId(), amount);
        BigDecimal currentBalance = processCreditVoucher(account.getId(), amount);
        return new Transaction(account,
                OperationTypeEnum.CREDIT_VOUCHER,
                amount,
                currentBalance,
                LocalDateTime.now());
    }

    private BigDecimal processCreditVoucher(final long accountId, final BigDecimal creditAmount) {
        List<TransactionEntity> userTransactions = transactionRepository.findDebitOperationsByAccountId(accountId);
        List<TransactionEntity> processedTransactions = new ArrayList<>();
        BigDecimal remainingBalance = creditAmount;
        for (TransactionEntity transactionEntity : userTransactions) {
            if (remainingBalance.equals(BigDecimal.ZERO)) {
                break;
            }
            BigDecimal newBalance = transactionEntity.getBalance().add(remainingBalance);
            if (newBalance.compareTo(BigDecimal.ZERO) > 0) {
                newBalance = BigDecimal.ZERO;
            }
            remainingBalance = remainingBalance.subtract(transactionEntity.getBalance().abs());
            transactionEntity.setBalance(newBalance);
            processedTransactions.add(transactionEntity);
            if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
                remainingBalance = BigDecimal.ZERO;
            }
        }
        transactionRepository.saveAll(processedTransactions);
        return remainingBalance;
    }

    @Override
    public OperationTypeEnum getOperationType() {
        return OperationTypeEnum.CREDIT_VOUCHER;
    }
}
