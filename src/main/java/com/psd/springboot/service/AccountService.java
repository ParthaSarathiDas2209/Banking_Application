package com.psd.springboot.service;

import com.psd.springboot.dto.AccountCreateDto;
import com.psd.springboot.dto.AccountDetailsDto;
import com.psd.springboot.dto.AccountSummaryDto;
import com.psd.springboot.dto.AccountUpdateDto;
import com.psd.springboot.entity.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountService {

        // ===========================
        // Public Access Methods
        // ===========================

        AccountSummaryDto createAccount(AccountCreateDto accountCreateDto);
        AccountSummaryDto getAccountSummaryById(Long id);
        AccountSummaryDto withdrawAndReturnSummary(Long id, @NotNull @Min(0) Double amount);
        AccountSummaryDto depositAndReturnSummary(Long id, @NotNull @Min(0) Double amount);
        List<AccountSummaryDto> getAllAccountSummaries();
        // ===========================
        // Admin/Internal Access Methods
        // ===========================

        AccountDetailsDto getAccountById(Long id);
        List<AccountDetailsDto> getAllAccounts();
        AccountDetailsDto updateAccount(Long id, AccountUpdateDto accountUpdateDto);
        boolean deleteAccount(Long id);
        void lockAccount(Long id);
        void unlockAccount(Long id);
        void setAccountActive(Long id, boolean isActive);
    }

