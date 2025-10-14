package com.pismo.transactions_app.api.transaction.v1;

import com.pismo.transactions_app.api.transaction.v1.request.TransactionRequestDto;
import com.pismo.transactions_app.api.transaction.v1.response.TransactionResponseDto;
import com.pismo.transactions_app.service.interfaces.ITransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> create(@Valid @RequestBody final TransactionRequestDto requestDto) {
        log.info("Received request to create transaction of typeId={} for accountId={} with amount={}",
                requestDto.getOperationTypeId(), requestDto.getAccountId(), requestDto.getAmount());
        final TransactionResponseDto response = transactionService.create(requestDto);
        log.info("Transaction created successfully: accountId={} id={}",
                response.getAccountId(), response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
