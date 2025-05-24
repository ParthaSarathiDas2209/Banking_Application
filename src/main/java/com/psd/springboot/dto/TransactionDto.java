package com.psd.springboot.dto;

import java.time.LocalDateTime;

public record TransactionDto(
        Long id,
        String type,
        double amount,
        LocalDateTime timestamp,
        String remarks,
        Long accountId
) {}
