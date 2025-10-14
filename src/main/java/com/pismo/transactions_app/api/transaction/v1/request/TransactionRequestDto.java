package com.pismo.transactions_app.api.transaction.v1.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionRequestDto {
    @NotNull
    private Long accountId;
    @NotNull
    private Long operationTypeId;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
}
