package com.pismo.transactions_app.api.account.v1.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pismo.transactions_app.domain.entities.AccountEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountResponseDto {
    private Long id;
    private String documentNumber;

    public static AccountResponseDto from(final AccountEntity accountEntity) {
        final AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(accountEntity.getId());
        accountResponseDto.setDocumentNumber(accountEntity.getDocumentNumber());
        return accountResponseDto;
    }
}
