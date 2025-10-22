package com.pismo.transactions_app.domain.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(final String message) {
        super(message);
    }

    public TransactionNotFoundException(final Long id) {
        super(String.format("There is no transaction for id: %s", id));
    }
}
