package com.psd.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/secure")
@PreAuthorize("hasRole('USER')")
public class UserSecureController {

    @GetMapping("/data")
    public ResponseEntity<String> getSecureData() {
        return ResponseEntity.ok("Secure user-only data.");
    }
}

