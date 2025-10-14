package com.pismo.transactions_app.api.transaction.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pismo.transactions_app.domain.entities.TransactionEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionResponseDto {
    private Long id;
    private Long accountId;
    private Long operationTypeId;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDate;

    public static TransactionResponseDto from(final TransactionEntity transactionEntity) {
        final TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transactionEntity.getId());
        transactionResponseDto.setAccountId(transactionEntity.getAccount().getId());
        transactionResponseDto.setOperationTypeId(transactionEntity.getOperationType().getId());
        transactionResponseDto.setAmount(transactionEntity.getAmount());
        transactionResponseDto.setEventDate(transactionEntity.getEventDate());
        return transactionResponseDto;
    }
}
