package com.pismo.transactions_app.service;

import com.pismo.transactions_app.api.account.v1.request.AccountRequestDto;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import com.pismo.transactions_app.domain.Account;
import com.pismo.transactions_app.domain.entities.AccountEntity;
import com.pismo.transactions_app.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions_app.domain.repository.interfaces.IAccountRepository;
import com.pismo.transactions_app.service.interfaces.IAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    @Override
    @Transactional
    public AccountResponseDto create(final AccountRequestDto requestDto) {
        final Account account = new Account(requestDto.getDocumentNumber());
        final AccountEntity accountEntity = accountRepository.save(AccountEntity.fromDomain(account));
        return AccountResponseDto.from(accountEntity);
    }

    @Override
    public AccountResponseDto getById(final long id) {
        final AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return AccountResponseDto.from(accountEntity);
    }
}
