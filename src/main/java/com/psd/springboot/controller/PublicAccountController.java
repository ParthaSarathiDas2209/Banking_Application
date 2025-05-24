package com.psd.springboot.controller;

import com.psd.springboot.dto.AccountCreateDto;
import com.psd.springboot.dto.AccountSummaryDto;
import com.psd.springboot.dto.TransactionRequest;
import com.psd.springboot.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/accounts/public")
public class PublicAccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountSummaryDto> createAccount(@RequestBody @Valid AccountCreateDto accountCreateDto){
        return new ResponseEntity<>(accountService.createAccount(accountCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountSummaryDto> getAccountSummary(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(accountService.getAccountSummaryById(id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountSummaryDto> deposit(@PathVariable Long id,
                                                     @RequestBody @Valid TransactionRequest request){
        return ResponseEntity.ok(accountService.depositAndReturnSummary(id, request.getAmount()));
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountSummaryDto> withdraw(@PathVariable Long id,
                                                      @RequestBody @Valid TransactionRequest request){
        return ResponseEntity.ok(accountService.withdrawAndReturnSummary(id, request.getAmount()));
    }

    @GetMapping
    public ResponseEntity<List<AccountSummaryDto>> getAllPublicAccounts(){
        return ResponseEntity.ok(accountService.getAllAccountSummaries());
    }
}

