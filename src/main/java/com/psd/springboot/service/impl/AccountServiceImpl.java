package com.psd.springboot.service.impl;

import com.psd.springboot.dto.AccountCreateDto;
import com.psd.springboot.dto.AccountDetailsDto;
import com.psd.springboot.dto.AccountSummaryDto;
import com.psd.springboot.dto.AccountUpdateDto;
import com.psd.springboot.entity.Account;
import com.psd.springboot.exception.AccountLockedException;
import com.psd.springboot.exception.AccountNotFoundException;
import com.psd.springboot.exception.AccountUnlockedException;
import com.psd.springboot.mapper.AccountMapper;
import com.psd.springboot.repository.AccountRepository;

import com.psd.springboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    // Inject repository for DB operations - marked final and injected by Lombok's @RequiredArgsConstructor
    private AccountRepository accountRepository;

    // Inject mapper to convert between entity and DTO objects
    private AccountMapper accountMapper;

    /**
     * Creates and saves a new Account with default values,
     * ignoring any client input other than those mapped by mapper (commented out).
     * Returns a summary DTO of the newly created account.
     */
    @Override
    public AccountSummaryDto createAccount(AccountCreateDto accountCreateDto) {
        // Creating new account entity manually instead of using mapper here
        Account account = new Account();

        // Set system default values for new account
        account.setActive(true);
        account.setLocked(false);
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountCreationDate(LocalDate.now());
        account.setAccountStatus("ACTIVE");
        account.setFailedLoginAttempts(0);
        account.setBalance(0.0);
        account.setLastTransactionDate(null);
        account.setLastLoginDate(null);

        // Save new account in DB
        Account savedAccount = accountRepository.save(account);

        // Convert saved entity to summary DTO and return
        return accountMapper.toAccountSummaryDto(savedAccount);
    }

    /**
     * Fetches Account by ID and returns a summary DTO.
     * Throws AccountNotFoundException if the account does not exist.
     */
    @Override
    public AccountSummaryDto getAccountSummaryById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        return accountMapper.toAccountSummaryDto(account);
    }

    /**
     * Withdraws an amount from account balance if sufficient funds are available.
     * Updates last transaction date.
     * Throws exceptions if account not found, amount invalid, or insufficient balance.
     */
    @Override
    public AccountSummaryDto withdrawAndReturnSummary(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id"));

        if(amount <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct amount from balance
        account.setBalance(account.getBalance() - amount);
        // Update last transaction date to start of current day
        account.setLastTransactionDate(LocalDate.now().atStartOfDay());

        // Save updated account state
        accountRepository.save(account);

        // Return updated account summary DTO
        return accountMapper.toAccountSummaryDto(account);
    }

    /**
     * Deposits an amount to the account balance if account is active and unlocked.
     * Throws exceptions for invalid amount or if account is inactive/locked.
     */
    @Override
    public AccountSummaryDto depositAndReturnSummary(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if(!account.isActive() || account.isLocked()){
            throw new RuntimeException("Account is either inactive or locked");
        }

        if(amount == null || amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        // Add amount to balance
        account.setBalance(account.getBalance() + amount);
        // Update last transaction date to start of current day
        account.setLastTransactionDate(LocalDate.now().atStartOfDay());

        // Save updated account state
        Account updatedAccount = accountRepository.save(account);

        // Return updated account summary DTO
        return accountMapper.toAccountSummaryDto(updatedAccount);
    }

    /**
     * Returns list of all accounts as AccountSummaryDto.
     */
    @Override
    public List<AccountSummaryDto> getAllAccountSummaries() {
        List<Account> accounts = accountRepository.findAll();

        // Map each Account entity to summary DTO
        return accounts.stream()
                .map(accountMapper::toAccountSummaryDto)
                .toList();
    }

    /**
     * Returns detailed information of a single account by ID.
     * Throws RuntimeException if account not found.
     */
    @Override
    public AccountDetailsDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return accountMapper.toAccountDetailsDto(account);
    }

    /**
     * Returns list of all accounts with detailed information.
     */
    @Override
    public List<AccountDetailsDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        // Map each account entity to details DTO
        return accounts.stream()
                .map(accountMapper::toAccountDetailsDto) // fixed to use injected mapper
                .collect(Collectors.toList());
    }

    /**
     * Deletes an account by ID.
     * Throws RuntimeException if account not found.
     * Returns true if delete succeeded.
     */
    @Override
    public boolean deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        accountRepository.delete(account);
        return true;
    }

    /**
     * Updates existing account details with values from AccountUpdateDto.
     * Only non-null fields from DTO are updated.
     * Sets updatedAt timestamp to current time.
     * Returns updated AccountDetailsDto.
     */
    @Override
    public AccountDetailsDto updateAccount(Long id, AccountUpdateDto accountUpdateDto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));

        if(accountUpdateDto.accountHolderName() != null){
            existingAccount.setAccountHolderName(accountUpdateDto.accountHolderName());
        }

        if(accountUpdateDto.contactEmail() != null){
            existingAccount.setContactEmail(accountUpdateDto.contactEmail());
        }

        if(accountUpdateDto.contactPhone() != null){
            existingAccount.setContactPhone(accountUpdateDto.contactPhone());
        }

        if(accountUpdateDto.accountStatus() != null){
            existingAccount.setAccountStatus(accountUpdateDto.accountStatus());
        }

        // Update timestamp to indicate modification time
        existingAccount.setUpdatedAt(LocalDateTime.now());

        // Save changes
        Account updatedAccount = accountRepository.save(existingAccount);

        // Return updated account details DTO
        return accountMapper.toAccountDetailsDto(updatedAccount);
    }

    /**
     * Locks the account if not already locked.
     * Throws AccountLockedException if the account is already locked.
     */
    @Override
    public void lockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

        if (account.isLocked()) {
            throw new AccountLockedException("Account is already locked.");
        }

        account.setLocked(true);
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }

    /**
     * Unlocks the account if currently locked.
     * Throws AccountUnlockedException if the account is already unlocked.
     */
    @Override
    public void unlockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

        if (!account.isLocked()) {
            throw new AccountUnlockedException("Account is already unlocked.");
        }

        account.setLocked(false);
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }

    /**
     * Sets the account's active status.
     * Throws IllegalArgumentException if trying to set the status already set.
     */
    @Override
    public void setAccountActive(Long id, boolean isActive) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

        if(account.isActive() == isActive){
            String status = isActive ? "ACTIVE" : "INACTIVE";
            throw new IllegalArgumentException("Account is already " + status + ".");
        }

        account.setActive(isActive);
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }
}
