package com.pismo.transactions_app.domain;

import com.pismo.transactions_app.domain.entities.AccountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.pismo.transactions_app.service.utils.ValidationUtils.requireNonNull;


@Getter
@Setter
@NoArgsConstructor
public class Account {
    private Long id;
    private String documentNumber;

    public Account(final String documentNumber) {
        this.documentNumber = requireNonNull(documentNumber, "document_number");
    }

    public static Account from(final AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setDocumentNumber(entity.getDocumentNumber());
        return account;
    }
}
