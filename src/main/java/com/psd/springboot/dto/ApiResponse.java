package com.psd.springboot.dto;

import java.time.Instant;

public record ApiResponse(
        boolean success,
        String message,
        String errorCode,
        Instant timeStamp
) {
    public ApiResponse(boolean success, String message){
        this(success, message,null, Instant.now());
    }

    public ApiResponse(boolean success, String message, String errorCode){
        this(success, message, errorCode, Instant.now());
    }
}