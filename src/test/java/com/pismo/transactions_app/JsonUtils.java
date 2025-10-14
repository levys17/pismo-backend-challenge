package com.pismo.transactions_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import com.pismo.transactions_app.api.transaction.v1.request.TransactionRequestDto;
import com.pismo.transactions_app.api.transaction.v1.response.TransactionResponseDto;
import com.pismo.transactions_app.domain.enums.OperationTypeEnum;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUtils {

    public static String getJsonValidAccountRequestDto() {
        return getJson("json/request/account/ValidAccountRequestDto.json");
    }

    public static String getJsonInvalidAccountRequestDto() {
        return getJson("json/request/account/InvalidAccountRequestDto.json");
    }

    public static AccountResponseDto getValidAccountResponseDto() {
        return getObject("json/response/account/ValidAccountResponseDto.json", AccountResponseDto.class);
    }

    public static TransactionRequestDto getValidTransactionRequestDtoForType(final OperationTypeEnum operationType) {
        return getObject(String.format("json/request/transaction/valid_%s.json", operationType.toString()), TransactionRequestDto.class);
    }

    public static TransactionResponseDto getValidTransactionResponseDtoForType(final OperationTypeEnum operationType) {
        return getObject(String.format("json/response/transaction/valid_%s.json", operationType), TransactionResponseDto.class);
    }

    public static TransactionRequestDto getInvalidTransactionRequestAccountIdNull() {
        return getObject("json/request/transaction/InvalidTransactionRequestAccountIdNull.json", TransactionRequestDto.class);
    }

    public static TransactionRequestDto getInvalidTransactionRequestOperationTypeIdNull() {
        return getObject("json/request/transaction/InvalidTransactionRequestOperationTypeIdNull.json", TransactionRequestDto.class);
    }

    public static TransactionRequestDto getInvalidTransactionRequestAmountNull() {
        return getObject("json/request/transaction/InvalidTransactionRequestAmountNull.json", TransactionRequestDto.class);
    }

    public static TransactionRequestDto getInvalidTransactionRequestOperationTypeInvalid() {
        return getObject("json/request/transaction/InvalidTransactionRequestOperationTypeInvalid.json", TransactionRequestDto.class);
    }

    private static String getJson(final String path) {
        try (InputStream is = getFile(path)) {
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + path, e);
        }
    }

    private static <T> T getObject(final String path, final Class<T> clazz) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = getFile(path)) {
            return objectMapper.readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + path, e);
        }
    }

    private static InputStream getFile(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
