package com.psd.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorDetails> buildErrorResponse(Exception ex, WebRequest request,
                                                            HttpStatus status, String errorCode) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                errorCode
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleAccountNotFound(AccountNotFoundException ex, WebRequest request) {
        logger.warn("Account not found: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND");
    }

    // If you have a generic ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        logger.warn("Resource not found: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorDetails> handleAccountLocked(AccountLockedException ex, WebRequest request) {
        logger.warn("Account locked: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.FORBIDDEN, "ACCOUNT_LOCKED");
    }

    @ExceptionHandler(AccountUnlockedException.class)
    public ResponseEntity<ErrorDetails> handleAccountUnlocked(AccountUnlockedException ex, WebRequest request) {
        logger.warn("Account unlocked: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST, "ACCOUNT_UNLOCKED");
    }

    @ExceptionHandler(OverdraftLimitExceededException.class)
    public ResponseEntity<ErrorDetails> handleOverdraftLimitExceeded(OverdraftLimitExceededException ex, WebRequest request) {
        logger.warn("Overdraft limit exceeded: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST, "OVERDRAFT_LIMIT_EXCEEDED");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(); // first validation error message
        logger.warn("Validation failed: {}", message);
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getDescription(false),
                "VALIDATION_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedAccess(UnauthorizedAccessException ex, WebRequest request) {
        logger.warn("Unauthorized access: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_ACCESS");
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientBalance(InsufficientBalanceException ex, WebRequest request) {
        logger.warn("Insufficient balance: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST, "INSUFFICIENT_BALANCE");
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateAccount(DuplicateAccountException ex, WebRequest request) {
        logger.warn("Duplicate account: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT, "DUPLICATE_ACCOUNT");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception: ", ex);
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    }
}