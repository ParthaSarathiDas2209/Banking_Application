package com.psd.springboot.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/secure")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSecureController {

    @GetMapping("/data")
    public String getSecureData() {
        return "Secure admin-only data.";
    }
}
