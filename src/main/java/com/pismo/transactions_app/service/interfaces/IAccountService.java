package com.pismo.transactions_app.service.interfaces;

import com.pismo.transactions_app.api.account.v1.request.AccountRequestDto;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import com.pismo.transactions_app.domain.Account;

import java.math.BigDecimal;

public interface IAccountService {

    AccountResponseDto create(final AccountRequestDto accountRequestDto);

    AccountResponseDto getById(final long id);
}
