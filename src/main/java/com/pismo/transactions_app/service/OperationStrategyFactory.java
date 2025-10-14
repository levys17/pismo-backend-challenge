package com.pismo.transactions_app.service;

import com.pismo.transactions_app.domain.enums.OperationTypeEnum;
import com.pismo.transactions_app.domain.exceptions.BusinessException;
import com.pismo.transactions_app.service.interfaces.IOperationStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OperationStrategyFactory {
    private final List<IOperationStrategy> strategies;
    private Map<OperationTypeEnum, IOperationStrategy> strategyMap = new HashMap<>();

    @PostConstruct
    void init() {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(IOperationStrategy::getOperationType, Function.identity()));
    }

    public IOperationStrategy getByOperationType(final OperationTypeEnum operationType) {
        return Optional.ofNullable(strategyMap.get(operationType))
                .orElseThrow(() -> new BusinessException("Invalid operation type: " + operationType));
    }

}
