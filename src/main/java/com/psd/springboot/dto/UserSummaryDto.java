package com.psd.springboot.dto;

import java.time.LocalDate;
import java.util.List;

public record UserSummaryDto(

     Long id,
     String fullName,
     String contactEmail,
     String contactPhone,
     AddressDto address,
     String panNumber,
     String nationality,
     LocalDate dateOfBirth,

     List<Long> accountIds

//    List<AccountSummaryDto> accounts
) {}


