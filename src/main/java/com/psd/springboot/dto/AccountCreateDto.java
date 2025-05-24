package com.psd.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

/**
 * DTO Name        : AccountCreateDto
 * Intended For    : Public/Internal API (Account Creation Requests)
 * Purpose         : Used for accepting new account creation requests.
 *                   This DTO captures the information needed to create
 *                   a new bank account, including contact and optional KYC data.
 *
 * Use Cases:
 * - New customer onboarding
 * - Account creation via self-service portals or internal admin tools
 *
 * Fields:
 * @param accountHolderName   Full name of the individual or entity creating the account.
 * @param accountType         Type of account requested (e.g., Savings, Current).
 * @param bankName            Name of the bank.
 * @param branchCode          Internal branch identifier where the account will be created.
 * @param ifscCode            IFSC code of the branch (for Indian systems).
 * @param currency            Currency in which the account operates (e.g., USD, INR).
 * @param contactEmail        Email address for communication and alerts.
 * @param contactPhone        Phone number of the account holder.
 * @param address             Physical address of the account holder.
 * @param balance             Initial deposit amount or opening balance.
 *
 * Optional KYC Fields:
 * @param panNumber           PAN (Permanent Account Number) for identity verification (India).
 * @param nationality         Nationality of the applicant.
 * @param dateOfBirth         Date of birth (used for KYC and validation).
 */

public record AccountCreateDto(

        @NotBlank String accountHolderName,
        @NotBlank String accountNumber,
        @NotBlank String accountType,
        @NotBlank String bankName,
        @NotBlank String branchCode,
        @NotBlank String ifscCode,
        @NotBlank String currency,

        @NotBlank @Email String contactEmail,
        @NotBlank String contactPhone,
        @NotBlank String address,

//      Balance
        @PositiveOrZero double balance,

        // Optional for KYC
        String panNumber,
        String nationality,
        @Past LocalDate dateOfBirth
) {}