package com.psd.springboot.mapper;

import com.psd.springboot.entity.User;
import com.psd.springboot.dto.UserSummaryDto;

import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(UserSummaryDto userSummaryDto){

        User user = new User();
        user.setId(userSummaryDto.id());
        user.setFullName(userSummaryDto.fullName());
        user.setContactEmail(userSummaryDto.contactEmail());
        user.setContactPhone(userSummaryDto.contactPhone());
        user.setPanNumber(userSummaryDto.panNumber());
        user.setNationality(userSummaryDto.nationality());
        user.setDateOfBirth(userSummaryDto.dateOfBirth());
//        user.setAddress(userSummaryDto.address());

        if(userSummaryDto.address() != null) {
            user.setAddress(AddressMapper.mapToAddress(userSummaryDto.address()));
        }else{
            user.setAddress(null);
        }

        return user;
    }

    public static UserSummaryDto mapToUserDto(User user){

        return new UserSummaryDto(
                user.getId(),
                user.getFullName(),
                user.getContactEmail(),
                user.getContactPhone(),
                AddressMapper.mapToAddressDto(user.getAddress()),
                user.getPanNumber(),
                user.getNationality(),
                user.getDateOfBirth(),

                user.getAccounts() != null
                        ? user.getAccounts().stream()
                        .map(account -> account.getId())
                        .collect(Collectors.toList())
                        : null

        );
    }
}
