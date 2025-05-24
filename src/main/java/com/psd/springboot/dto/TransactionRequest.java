package com.psd.springboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    @NotNull(message = "Amount is required")
    @Min(value = 0, message ="Amount must be positive")
    private Double amount;

    private String description;   // Optional: "ATM withdrawal", "Online deposit", etc.

    @Pattern(regexp = "DEPOSIT|WITHDRAWAL", message = "Transaction type must be Deposit or Withdrawl")
    private String transactionType; // Optional: "DEPOSIT", "WITHDRAWAL"

}