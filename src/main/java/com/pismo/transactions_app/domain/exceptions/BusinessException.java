package com.pismo.transactions_app.domain.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }
}
