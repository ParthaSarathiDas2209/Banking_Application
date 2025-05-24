package com.psd.springboot.mapper;

import com.psd.springboot.entity.Address;
import com.psd.springboot.dto.AddressDto;

public class AddressMapper {

    public static Address mapToAddress(AddressDto addressDto) {

                Address address = new Address();
                address.setId(addressDto.id());
                address.setState(addressDto.street());
                address.setCity(addressDto.city());
                address.setState(addressDto.state());
                address.setPostalCode(addressDto.postalCode());
                address.setCountry(addressDto.country());
                return address;
            }
    public static AddressDto mapToAddressDto(Address address) {

        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }

}