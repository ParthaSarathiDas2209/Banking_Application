package com.psd.springboot.controller;

import com.psd.springboot.dto.AccountDetailsDto;
import com.psd.springboot.dto.AccountUpdateDto;
import com.psd.springboot.dto.ApiResponse;
import com.psd.springboot.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/internal") // Base path for internal/admin APIs
@PreAuthorize("hasRole('ADMIN')")  // Restrict all methods to users with ADMIN Role
public class AdminAccountController {

    @Autowired
    private AccountService accountService;


    // 🔍 1. Get full details of a specific account by ID
    @GetMapping("/account-details/{id}")
    public ResponseEntity<AccountDetailsDto> getFullAccountInfo(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // 📄 2. Get all accounts with full internal details
    @GetMapping
    public ResponseEntity<List<AccountDetailsDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // ❌ 3. Delete an account by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
//        if(accountService.deleteAccount(id)){
//            return ResponseEntity.ok("Account has been deleted Successfully...");
//        }else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found...");
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Long id){
        boolean deleted = accountService.deleteAccount(id);
        if(deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "Account deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Account not found", "ACCOUNT_NOT_FOUND"));
        }
    }


    // ✏️ 4. Update account fields (email, phone, overdraft, etc.)
    @PutMapping("/{id}/update")
    public ResponseEntity<AccountDetailsDto> updateAccount(@PathVariable Long id,
                                                           @RequestBody @Valid AccountUpdateDto updateDto){
        AccountDetailsDto updated = accountService.updateAccount(id,updateDto);
        return ResponseEntity.ok(updated);
    }

//    @PutMapping("/{id}/update")
//    public ResponseEntity<ApiResponse> updateAccount(@PathVariable Long id,
//                                                     @RequestBody @Valid AccountUpdateDto updateDto) {
//        AccountDetailsDto updated = accountService.updateAccount(id, updateDto);
//        return ResponseEntity.ok(new ApiResponse(true, "Account updated successfully", null, updated));
//    }


    // 🔒 5. Lock or unlock account manually
//    @PutMapping("/{id}/lock")
//    public ResponseEntity<String> lockAccount(@PathVariable Long id){
//        accountService.lockAccount(id);
//        return ResponseEntity.ok("Account locked Successfully...");
//    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<ApiResponse> lockAccount(@PathVariable Long id) {
        accountService.lockAccount(id);
        return ResponseEntity.ok(new ApiResponse(true, "Account locked successfully"));
    }

//    @PutMapping("/{id}/unlock")
//    public ResponseEntity<String> unlockAccount(@PathVariable Long id){
//        accountService.unlockAccount(id);
//        return ResponseEntity.ok("Account unlocked Successfully...");
//    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<ApiResponse> unlockAccount(@PathVariable Long id){
        accountService.unlockAccount(id);
        return ResponseEntity.ok(new ApiResponse(true, "Account unlocked Successfully"));
    }

    // ✅ 6. Activate or deactivate an account
//    @PutMapping("/{id}/activate")
//    public ResponseEntity<String> activateAccount(@PathVariable Long id){
//        accountService.setAccountActive(id, true);
//        return ResponseEntity.ok("Account Activated Successfully...");
//    }

        @PutMapping("/{id}/activate")
        public ResponseEntity<ApiResponse> activateAccount(@PathVariable Long id){
        accountService.setAccountActive(id, true);
        return ResponseEntity.ok(new ApiResponse(true,"Your Account have been activated"));
        }


//    @PutMapping("/{id}/deactivate")
//    public ResponseEntity<String> deactivateAccount(@PathVariable Long id){
//        accountService.setAccountActive(id, false);
//        return ResponseEntity.ok("Account Deactivated Successfully...");
//    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse> deactivateAccount(@PathVariable Long id){
        accountService.setAccountActive(id, false);
        return ResponseEntity.ok(new ApiResponse(true, "Your Account have been De-activated"));
    }

//    @PutMapping("/{id}/status")
//    public ResponseEntity<Void> setAccountStatus(@PathVariable Long id, @RequestParam boolean active) {
//        accountService.setAccountActive(id, active);
//        String message = active ? "Account activated successfully" : "Account deactivated Successfully";
//        return ResponseEntity.ok(new ApiResponse(true, message));
//    }

}
