package com.pismo.transactions_app.domain.entities;

import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "operation_types")
@Data
public class OperationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, updatable = false)
    private String description;

    public static OperationTypeEntity fromEnum(final OperationTypeEnum operationTypeEnum) {
        final OperationTypeEntity operationTypeEntity = new OperationTypeEntity();
        operationTypeEntity.setId(operationTypeEnum.getId());
        operationTypeEntity.setDescription(operationTypeEnum.getDescription());
        return operationTypeEntity;
    }

}
