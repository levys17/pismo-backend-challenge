package com.pismo.transactions_app.api.exceptionhandler;

import lombok.Getter;

import java.net.URI;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    BUSINESS_ERROR("/business-error", "Business Error"),
    INVALID_DATA("/invalid-data", "Invalid data"),
    SYSTEM_ERROR("/system-error", "Internal Error");

    private final String title;
    private final URI uri;

    ProblemType(final String path, final String title) {
        this.uri = URI.create("/error" + path);
        this.title = title;
    }

}