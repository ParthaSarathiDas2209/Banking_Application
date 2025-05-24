package com.psd.springboot.exception;

public class OverdraftLimitExceededException extends RuntimeException {
    public OverdraftLimitExceededException(String message) {
        super(message);
    }
}
