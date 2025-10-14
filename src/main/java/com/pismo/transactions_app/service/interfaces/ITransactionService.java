package com.pismo.transactions_app.service.interfaces;

import com.pismo.transactions_app.api.transaction.v1.request.TransactionRequestDto;
import com.pismo.transactions_app.api.transaction.v1.response.TransactionResponseDto;

public interface ITransactionService {

    TransactionResponseDto create(final TransactionRequestDto request);
}
