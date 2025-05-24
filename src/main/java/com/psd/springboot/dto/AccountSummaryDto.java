package com.psd.springboot.dto;

import java.time.LocalDate;

/**
 * DTO Name        : AccountSummaryDto
 * Intended For    : Public / Client-facing APIs
 * Purpose         : Provides basic, non-sensitive summary information about a bank account,
 *                   suitable for display in end-user applications (e.g., mobile or web apps).
 *
 * This DTO avoids exposing sensitive or internal fields like PAN, IFSC, SWIFT, audit data, etc.
 * It is intended to maintain security while giving users enough information to view and manage
 * their accounts at a summary level.
 *
 * Fields:
 * @param id                  Unique identifier for the account.
 * @param accountHolderName   Name of the person or entity holding the account.
 * @param maskedAccountNumber       Public-facing account number (masked if needed on UI).
 * @param accountType         Type of the account (e.g., Savings, Checking).
 * @param bankName            Name of the bank where the account is held.
 * @param currency            Currency used for the account (e.g., USD, INR).
 * @param accountCreationDate Date when the account was originally created.
 * @param balance             Current available balance in the account.
 * @param active              Whether the account is currently active.
 */

public record AccountSummaryDto(
        Long id,
        String accountHolderName,
        String maskedAccountNumber,
        String accountType,
        String bankName,
        String currency,
        LocalDate accountCreationDate,
        double balance,
        boolean active,
        String branchDisplayName,
        String displayStatus
) {}
