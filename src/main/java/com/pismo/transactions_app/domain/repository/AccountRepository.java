package com.pismo.transactions_app.domain.repository;

import com.pismo.transactions_app.domain.entities.AccountEntity;
import com.pismo.transactions_app.domain.repository.interfaces.IAccountJpaRepository;
import com.pismo.transactions_app.domain.repository.interfaces.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepository implements IAccountRepository {

    private final IAccountJpaRepository accountJpaRepository;

    @Override
    public AccountEntity save(final AccountEntity accountEntity) {
        return accountJpaRepository.save(accountEntity);
    }

    @Override
    public Optional<AccountEntity> findById(final long id) {
        return accountJpaRepository.findById(id);
    }
}
