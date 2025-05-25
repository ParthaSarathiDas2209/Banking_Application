package com.psd.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity                  // Enables Spring Security’s web security support
@EnableMethodSecurity               // Enables method-level security annotations like @PreAuthorize
public class SecurityConfig {

    // Defines the main security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (common for REST APIs without sessions)
                .csrf(AbstractHttpConfigurer::disable)

                // Define authorization rules based on URL patterns and roles
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints accessible without authentication
                        .requestMatchers("/api/accounts/public/**").permitAll()

                        // Admin-only secure endpoints (matches your AdminSecureController)
                        .requestMatchers("/api/admin/secure/**").hasRole("ADMIN")

                        // Admin-only dashboard endpoints (matches your AdminDashboardController)
                        .requestMatchers("/api/admin/dashboard/**").hasRole("ADMIN")

                        // User-only secure endpoints (matches your UserSecureController)
                        .requestMatchers("/api/user/secure/**").hasRole("USER")

                        // User dashboard accessible by USER or ADMIN roles
                        .requestMatchers("/api/user/dashboard/**").hasAnyRole("USER", "ADMIN")

                        // Any other requests require authentication
                        .anyRequest().authenticated()
                )

                // Enable HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults());

        return http.build();  // Build and return the SecurityFilterChain
    }

    // In-memory user store with two users: admin and user
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password("{noop}admin123") // {noop} means no password encoding, plaintext
                        .roles("ADMIN")
                        .build(),

                User.withUsername("user")
                        .password("{noop}user123")
                        .roles("USER")
                        .build()
        );
    }
}
