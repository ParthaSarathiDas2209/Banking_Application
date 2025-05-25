package com.psd.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/dashboard")
@PreAuthorize("hasRole('USER')")
public class UserDashboardController {

    @GetMapping("/overview")
    public ResponseEntity<String> getUserOverview() {
        return ResponseEntity.ok("User dashboard overview data");
    }
}

