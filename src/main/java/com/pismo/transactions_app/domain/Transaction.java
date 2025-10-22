package com.pismo.transactions_app.domain;

import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static com.pismo.transactions_app.service.utils.ValidationUtils.requireNonNull;
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private Long id;
    private Account account;
    private OperationTypeEnum operationType;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime eventDate;

    public Transaction(final Account account,
                       final OperationTypeEnum operationTypeEnum,
                       final BigDecimal amount,
                       final BigDecimal balance,
                       final LocalDateTime eventDate) {
        this.account = requireNonNull(account, "Account");
        this.operationType = requireNonNull(operationTypeEnum, "Operation Type");
        this.amount = requireNonNull(amount, "Amount");
        this.balance = balance;
        this.eventDate = requireNonNull(eventDate, "Event Date");
    }
}
