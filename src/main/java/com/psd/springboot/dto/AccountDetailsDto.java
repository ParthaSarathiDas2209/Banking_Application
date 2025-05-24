package com.psd.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO Name        : AccountDetailsDto
 * Intended For    : Admin / Internal Use Only
 * Purpose         : Provides a complete and detailed view of a user's bank account,
 *                   including personal details, account configuration, international banking data,
 *                   audit tracking, and security status.
 *
 * Use Cases:
 * - Admin dashboards
 * - KYC (Know Your Customer) processes
 * - Audit and security monitoring
 * - Internal support tools
 *
 * NOTE: This DTO contains **sensitive** and **internal-use-only** fields. It should never be exposed
 * directly to public or client-facing APIs.
 *
 * Fields:
 * @param id                   Unique identifier for the account.
 * @param accountHolderName    Full name of the account holder.
 * @param accountNumber        Unique account number.
 * @param accountType          Type of the account (e.g., Savings, Checking).
 * @param bankName             Name of the bank holding the account.
 * @param branchCode           Code identifying the specific bank branch.
 * @param ifscCode             IFSC code for Indian banking system.
 * @param currency             Currency used in the account (e.g., USD, INR).
 * @param accountCreationDate  Date the account was originally opened.
 * @param balance              Current balance available in the account.
 * @param active               Indicates whether the account is currently active.
 *
 * Contact Info:
 * @param panNumber            PAN number for tax and identification purposes (India).
 * @param nationality          Nationality of the account holder.
 * @param dateOfBirth          Birth date of the account holder.
 *
 * Account Management:
 * @param accountStatus        Status of the account (e.g., Active, Suspended, Closed).
 * @param overdraftAllowed     Indicates if overdraft facility is enabled.
 * @param overdraftLimit       The maximum overdraft limit if applicable.
 *
 * International Banking:
 * @param swiftCode            SWIFT code for international money transfers.
 * @param iban                 IBAN for cross-border payments and identification.
 *
 * Audit Fields:
 * @param lastTransactionDate  Date and time of the last recorded transaction.
 * @param lastLoginDate        Date and time the user last accessed the system.
 * @param createdAt            Timestamp of when the account record was created.
 * @param updatedAt            Timestamp of the most recent update to the record.
 *
 * Security:
 * @param locked               Whether the account is locked due to security or policy reasons.
 * @param failedLoginAttempts  Count of consecutive failed login attempts.
 *
 * Ownership:
 * @param userId               Reference to the owning user (foreign key).
 */

public record AccountDetailsDto(

        // Basic Account Info
        Long id,

        @JsonProperty("accountHolderName")
        @NotNull(message = "Name is required")
        String accountHolderName,

        @NotBlank(message = "Account number is required")
        String accountNumber,

        String accountType,
        String bankName,
        String branchCode,
        String ifscCode,
        String currency,
        LocalDate accountCreationDate,
        boolean active,


        // Contact Info
      @NotBlank(message = "Email cannot be blank")
      @Email(message = "Invalid email format")
        String contactEmail,

      @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
        String contactPhone,
        String address,

//      Balance
        @PositiveOrZero double balance,

        // Optional for KYC
        // Personal Identity
        String panNumber,
        String nationality,

        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        // Account Management
        String accountStatus,
        boolean overdraftAllowed,
        double overdraftLimit,

        // International Banking
        String swiftCode,
        String iban,

        // Audit Fields
        LocalDateTime lastTransactionDate,
        LocalDateTime lastLoginDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        // Security
        boolean locked,
        int failedLoginAttempts,

        // Ownership
        Long userId
) {}
