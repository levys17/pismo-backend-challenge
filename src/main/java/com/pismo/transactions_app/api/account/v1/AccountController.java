package com.pismo.transactions_app.api.account.v1;

import com.pismo.transactions_app.api.account.v1.request.AccountRequestDto;
import com.pismo.transactions_app.api.account.v1.response.AccountResponseDto;
import com.pismo.transactions_app.service.interfaces.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@Valid @RequestBody final AccountRequestDto accountRequestDto) {
        log.info("Received request to create account for documentNumber={}", accountRequestDto.getDocumentNumber());
        final AccountResponseDto response = accountService.create(accountRequestDto);
        log.info("Account created successfully: documentNumber={} id={}", response.getDocumentNumber(), response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getById(@PathVariable(value = "id") final Long id) {
        log.info("Received request to get account with id={}", id);
        final AccountResponseDto response = accountService.getById(id);
        log.info("Account retrieved successfully: id={} documentNumber={}", response.getId(), response.getDocumentNumber());
        return ResponseEntity.ok().body(response);
    }
}
