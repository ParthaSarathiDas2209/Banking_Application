package com.psd.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a bank account in the system.
 * Includes basic account info, contact, KYC details,
 * international banking fields, audit logs, and user access controls.
 */

@Getter
@Setter
@Builder
@ToString(exclude = {"transactions", "user"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accounts")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id; // Primary key

    // =========================
    // Basic Account Information
    // =========================
    @Column(length = 30, nullable = false)
    private String accountHolderName;

    @Column(length = 20, unique = true, nullable = false)
    private String accountNumber; // Unique account number (e.g., IBAN equivalent)

    private String accountType;   // e.g., SAVINGS, CURRENT
    private String bankName;
    private String branchCode;
    private String ifscCode;      // Used in India for routing
    private String currency;      // e.g., USD, INR, EUR

    private LocalDate accountCreationDate;
    private double balance;
    private boolean active;       // Indicates if account is currently active

    @NotBlank @Email String contactEmail;
    @NotBlank String contactPhone;
    @NotBlank String address;

    // Optional for KYC
    String panNumber;
    String nationality;
    @Past
    LocalDate dateOfBirth;

    // =========================
    // Account Management Status
    // =========================
    private String accountStatus; // e.g., ACTIVE, SUSPENDED, CLOSED
    private boolean overdraftAllowed;
    private double overdraftLimit;

    // =========================
    // International Banking
    // =========================
    private String swiftCode;     // SWIFT/BIC for international transfers
    private String iban;          // IBAN format (for Europe)

    // =========================
    // Auditing Fields
    // =========================
    private LocalDateTime lastTransactionDate;
    private LocalDateTime lastLoginDate;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  // Automatically set when account is created

    @LastModifiedDate
    private LocalDateTime updatedAt;  // Automatically updated when record changes

    // =========================
    // Security & Access Control
    // =========================
    private boolean locked;           // Indicates if account access is locked
    private int failedLoginAttempts;  // Used for brute-force protection

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // =========================
    // Account Ownership
    // =========================
//    private Long userId; // Reference to the owning user (optional; can be a foreign key)


}
