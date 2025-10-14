package com.pismo.transactions_app.service.utils;

import com.pismo.transactions_app.domain.exceptions.BusinessException;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static <T> T requireNonNull(final T obj, final String field) {
        if (obj == null) {
            throw new BusinessException(String.format("%s is required.", field));
        }
        return obj;
    }
}
