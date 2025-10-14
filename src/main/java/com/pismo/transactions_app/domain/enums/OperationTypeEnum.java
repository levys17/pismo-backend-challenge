package com.pismo.transactions_app.domain.enums;

import com.pismo.transactions_app.domain.exceptions.BusinessException;
import lombok.Getter;

@Getter
public enum OperationTypeEnum {
    NORMAL_PURCHASE(1, "Normal Purchase"),
    INSTALLMENT_PURCHASE(2, "Purchase with installments"),
    WITHDRAWAL(3, "Withdrawal"),
    CREDIT_VOUCHER(4, "Credit Voucher");

    private final long id;
    private final String description;

    OperationTypeEnum(final long id, final String description) {
        this.id = id;
        this.description = description;
    }

    public static OperationTypeEnum fromId(long id) {
        for (OperationTypeEnum type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new BusinessException("Invalid OperationTypeId: " + id);
    }
}

