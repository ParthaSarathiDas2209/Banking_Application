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
@EnableMethodSecurity               // Enables method-level security (e.g. @PreAuthorize)
public class SecurityConfig {

    // Defines the main security filter chain for handling HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF protection (good for stateless APIs or development)
                .csrf(AbstractHttpConfigurer::disable)

                // Configures URL-based authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints accessible by anyone (no authentication required)
                        .requestMatchers("/api/accounts/public/**").permitAll()

                        // Admin endpoints accessible only to users with ROLE_ADMIN
                        .requestMatchers("/api/accounts/internal/**").hasRole("ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )

                // Enables basic HTTP authentication with default settings
                .httpBasic(Customizer.withDefaults());

        return http.build();  // Builds and returns the configured security filter chain
    }

    // In-memory user details for development/testing purposes
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                // Admin user with ADMIN role
                User.withUsername("admin")
                        .password("{noop}admin123") // {noop} indicates no password encoding
                        .roles("ADMIN")
                        .build(),

                // Regular user with USER role
                User.withUsername("user")
                        .password("{noop}user123")
                        .roles("USER")
                        .build()
        );
    }
}
