package com.pismo.transactions_app.domain.repository.interfaces;

import com.pismo.transactions_app.domain.entities.AccountEntity;

import java.util.Optional;

public interface IAccountRepository {

    AccountEntity save(final AccountEntity entity);

    Optional<AccountEntity> findById(final long id);
}
