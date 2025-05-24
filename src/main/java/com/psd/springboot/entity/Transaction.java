package com.psd.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString(exclude = {"account"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
