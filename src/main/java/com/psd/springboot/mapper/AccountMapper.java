package com.psd.springboot.mapper;

import com.psd.springboot.dto.AccountCreateDto;
import com.psd.springboot.dto.AccountSummaryDto;
import com.psd.springboot.entity.Account;
import com.psd.springboot.dto.AccountDetailsDto;

public class AccountMapper {

    public static Account mapToAccount(AccountDetailsDto accountDetailsDto) {

        Account account = new Account();

        account.setId(accountDetailsDto.id());
        account.setAccountHolderName(accountDetailsDto.accountHolderName());
        account.setAccountNumber(accountDetailsDto.accountNumber());
        account.setAccountType(accountDetailsDto.accountType());
        account.setBankName(accountDetailsDto.bankName());
        account.setBranchCode(accountDetailsDto.branchCode());
        account.setIfscCode(accountDetailsDto.ifscCode());
        account.setCurrency(accountDetailsDto.currency());
        account.setAccountCreationDate(accountDetailsDto.accountCreationDate());
        account.setBalance(accountDetailsDto.balance());
        account.setActive(accountDetailsDto.active());
        account.setContactEmail(accountDetailsDto.contactEmail());
        account.setContactPhone(accountDetailsDto.contactPhone());
        account.setAddress(accountDetailsDto.address());

        // Personal Identity
        account.setPanNumber(accountDetailsDto.panNumber());
        account.setNationality(accountDetailsDto.nationality());
        account.setDateOfBirth(accountDetailsDto.dateOfBirth());

        // Additional defaults you may want to set:
        account.setActive(true);
        account.setAccountStatus("Active");
        account.setCreatedAt(java.time.LocalDateTime.now());
        account.setUpdatedAt(java.time.LocalDateTime.now());

        // Account Management
        account.setAccountStatus(accountDetailsDto.accountStatus());
        account.setOverdraftAllowed(accountDetailsDto.overdraftAllowed());
        account.setOverdraftLimit(accountDetailsDto.overdraftLimit());

        // International
        account.setSwiftCode(accountDetailsDto.swiftCode());
        account.setIban(accountDetailsDto.iban());

        // Audit
        account.setLastTransactionDate(accountDetailsDto.lastTransactionDate());
        account.setLastLoginDate(accountDetailsDto.lastLoginDate());
        account.setCreatedAt(accountDetailsDto.createdAt());
        account.setUpdatedAt(accountDetailsDto.updatedAt());

        // Security
        account.setLocked(accountDetailsDto.locked());
        account.setFailedLoginAttempts(accountDetailsDto.failedLoginAttempts());

        return account;
    }

    public static AccountDetailsDto mapToAccountDto(Account account) {
        return new AccountDetailsDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBankName(),
                account.getBranchCode(),
                account.getIfscCode(),
                account.getCurrency(),
                account.getAccountCreationDate(),

                // Contact Info
                account.isActive(),
                account.getContactEmail(),
                account.getContactPhone(),
                account.getAddress(),
                account.getBalance(),

                // Personal Identity
                account.getPanNumber(),
                account.getNationality(),
                account.getDateOfBirth(),

                // Account Management
                account.getAccountStatus(),
                account.isOverdraftAllowed(),
                account.getOverdraftLimit(),

                // International
                account.getSwiftCode(),
                account.getIban(),

                // Audit Fields
                account.getLastTransactionDate(),
                account.getLastLoginDate(),
                account.getCreatedAt(),
                account.getUpdatedAt(),


                // Security
                 account.isLocked(),
                 account.getFailedLoginAttempts(),

                // Ownership
                account.getUser() != null ? account.getUser().getId() : null
            );
        }


    // ✅ ADD THIS NEW METHOD BELOW
    public AccountSummaryDto toAccountSummaryDto(Account account) {
        return new AccountSummaryDto(
                account.getId(),
                account.getAccountHolderName(),
                maskAccountNumber(account.getAccountNumber()),
                account.getAccountType(),
                account.getBankName(),
                account.getCurrency(),
                account.getAccountCreationDate(),
                account.getBalance(),
                account.isActive(),
                account.getBranchCode(),  // or a friendly branch display name
                account.getAccountStatus() // or a user-friendly status label
        );
    }

    // ✅ Helper method to mask account number
    private static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "**** **** **** " + accountNumber.substring(accountNumber.length() - 4);
    }

    public static Account mapToAccount(AccountCreateDto accountCreateDto) {
        Account account = new Account();
        account.setAccountHolderName(accountCreateDto.accountHolderName());
        account.setAccountNumber(accountCreateDto.accountNumber());
        account.setAccountType(accountCreateDto.accountType());
        account.setBankName(accountCreateDto.bankName());
        account.setBranchCode(accountCreateDto.branchCode());
        account.setIfscCode(accountCreateDto.ifscCode());
        account.setCurrency(accountCreateDto.currency());
        account.setContactEmail(accountCreateDto.contactEmail());
        account.setContactPhone(accountCreateDto.contactPhone());
        account.setAddress(accountCreateDto.address());
        account.setPanNumber(accountCreateDto.panNumber());
        account.setNationality(accountCreateDto.nationality());
        account.setDateOfBirth(accountCreateDto.dateOfBirth());

        // Defaults
        account.setCreatedAt(java.time.LocalDateTime.now());
        account.setUpdatedAt(java.time.LocalDateTime.now());
        account.setAccountStatus("ACTIVE");

        return account;
    }

    public AccountDetailsDto toAccountDetailsDto(Account account) {

        return new AccountDetailsDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBankName(),
                account.getBranchCode(),
                account.getIfscCode(),
                account.getCurrency(),
                account.getAccountCreationDate(),

                // Contact Info
                account.isActive(),
                account.getContactEmail(),
                account.getContactPhone(),
                account.getAddress(),
                account.getBalance(),

                // Personal Identity
                account.getPanNumber(),
                account.getNationality(),
                account.getDateOfBirth(),

                // Account Management
                account.getAccountStatus(),
                account.isOverdraftAllowed(),
                account.getOverdraftLimit(),

                // International
                account.getSwiftCode(),
                account.getIban(),

                // Audit Fields
                account.getLastTransactionDate(),
                account.getLastLoginDate(),
                account.getCreatedAt(),
                account.getUpdatedAt(),

                // Security
                account.isLocked(),
                account.getFailedLoginAttempts(),

                // User ID (Ownership)
                account.getUser() != null ? account.getUser().getId() : null
        );
    }

}

