package com.pismo.transactions_app.domain.entities;

import com.pismo.transactions_app.domain.Transaction;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationTypeEntity operationType;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(name = "event_date", nullable = false, updatable = false)
    private LocalDateTime eventDate;

    public static TransactionEntity fromDomain(final Transaction transaction) {
        final TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(AccountEntity.fromDomain(transaction.getAccount()));
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setOperationType(OperationTypeEntity.fromEnum(transaction.getOperationType()));
        transactionEntity.setEventDate(transaction.getEventDate());
        return transactionEntity;
    }
}
