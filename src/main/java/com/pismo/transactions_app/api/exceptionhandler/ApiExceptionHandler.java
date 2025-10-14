package com.pismo.transactions_app.api.exceptionhandler;

import com.pismo.transactions_app.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions_app.domain.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private static final String INTERNAL_ERROR_MSG = "An unexpected error occured. Please try again.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(final Exception ex) {
        final HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ProblemDetail problemDetail = buildProblemDetail(status, ProblemType.SYSTEM_ERROR, INTERNAL_ERROR_MSG);
        log.error("{}: {}", ProblemType.SYSTEM_ERROR.getTitle(), ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(final BusinessException ex) {
        final HttpStatusCode status = HttpStatus.BAD_REQUEST;
        final ProblemDetail problemDetail = buildProblemDetail(status, ProblemType.BUSINESS_ERROR, ex.getMessage());
        log.error("{}: {}", ProblemType.BUSINESS_ERROR.getTitle(), ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(final AccountNotFoundException ex) {
        final HttpStatusCode status = HttpStatus.NOT_FOUND;
        final ProblemDetail problemDetail = buildProblemDetail(status, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage());
        log.error("{}: {}", ProblemType.RESOURCE_NOT_FOUND.getTitle(), ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(final MethodArgumentNotValidException ex) {
        final  String message = "One or more fields contain invalid data. Please review your request and try again.";
        final HttpStatusCode status = HttpStatus.BAD_REQUEST;
        final ProblemDetail problemDetail = buildProblemDetail(status, ProblemType.INVALID_DATA, message);
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .toList();
        problemDetail.setProperty("Errors", errors);
        log.error("{}: {}", ProblemType.INVALID_DATA.getTitle(), ex.getMessage());
        return new ResponseEntity<>(problemDetail, status);
    }

    private ProblemDetail buildProblemDetail(final HttpStatusCode status, final ProblemType problemType, final String detail) {
        final  ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(detail);
        problemDetail.setType(problemType.getUri());
        problemDetail.setTitle(problemType.getTitle());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
