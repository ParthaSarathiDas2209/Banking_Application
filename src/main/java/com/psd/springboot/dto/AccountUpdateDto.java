package com.psd.springboot.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO Name        : AccountUpdateDto
 * Intended For    : Internal/Admin APIs (Account Maintenance)
 * Purpose         : Represents the fields that can be updated in an existing account.
 *                   Used for patching or updating account-level settings, contact info,
 *                   and account configuration via admin tools or automated workflows.
 *
 * Use Cases:
 * - Admin editing account details (e.g., contact info, status changes)
 * - Enabling/disabling overdraft
 * - Locking/unlocking accounts
 * - Updating operational status (active/inactive)
 *
 * Fields:
 * @param contactEmail        Updated email address for the account holder.
 * @param contactPhone        Updated phone number.
 * @param address             Updated physical address.
 * @param accountStatus       New account status (e.g., Active, Suspended, Closed).
 * @param active              Flag to mark the account as active or inactive.
 * @param locked              Flag to lock or unlock the account.
 * @param overdraftAllowed    Whether overdraft functionality is enabled.
 * @param overdraftLimit      Maximum overdraft limit (if allowed).
 */

public record AccountUpdateDto(

        @NotBlank(message = "Account Holder Name must not be blank")
        String accountHolderName,

        @Email(message = "Invalid email format")
        String contactEmail,

        @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
        String contactPhone,

//       Admin-only fields
        String address,
        String accountStatus,
        Boolean active,
        boolean locked,
        boolean overdraftAllowed,

        @PositiveOrZero(message = "Overdraft limit must be zero or positive")
        double overdraftLimit
) {}
