package com.psd.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ToString(exclude = {"accounts"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    // =========================
    // Contact Information
    // =========================
    private String fullName;
    private String contactEmail;
    private String contactPhone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    // =========================
    // KYC (Know Your Customer)
    // =========================

        private String panNumber;     // Permanent Account Number (India) or Tax ID
        private String nationality;
        private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

}
