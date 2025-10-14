package com.pismo.transactions_app.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final Long id) {
        super(String.format("There is no account for id: %s", id));
    }
}
