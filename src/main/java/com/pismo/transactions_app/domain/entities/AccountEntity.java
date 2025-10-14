package com.pismo.transactions_app.domain.entities;

import com.pismo.transactions_app.domain.Account;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_number", nullable = false, updatable = false)
    private String documentNumber;

    public static AccountEntity fromDomain(final Account account) {
        final AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(account.getId());
        accountEntity.setDocumentNumber(account.getDocumentNumber());
        return accountEntity;
    }
}
