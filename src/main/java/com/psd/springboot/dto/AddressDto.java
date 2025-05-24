package com.psd.springboot.dto;

public record AddressDto(

        Long id,
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {}
