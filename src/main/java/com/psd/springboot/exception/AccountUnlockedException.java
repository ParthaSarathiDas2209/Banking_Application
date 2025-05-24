package com.psd.springboot.exception;

public class AccountUnlockedException extends RuntimeException {
    public AccountUnlockedException(String message) {
        super(message);
    }
}
