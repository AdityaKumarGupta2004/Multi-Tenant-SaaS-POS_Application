package com.trisys.Pos.System.mapper;


import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User savedUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setFirstName(savedUser.getFirstName());
        userDTO.setLastName(savedUser.getLastName());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setPhoneNumber(savedUser.getPhoneNumber());
        userDTO.setAddress(savedUser.getAddress());
//        userDTO.setGender(savedUser.getGender());
        userDTO.setDateOfBirth(savedUser.getDateOfBirth());
        userDTO.setRole(savedUser.getRole());
        userDTO.setRegisteredAt(savedUser.getRegisteredAt());
        userDTO.setUpdatedAt(savedUser.getUpdatedAt());
        userDTO.setLastLoginAt(savedUser.getLastLoginAt());
        userDTO.setProfileImageId(savedUser.getProfileImageId());
//        userDTO.setStoreId(savedUser.getStore() != null ? savedUser.getStore().getId() : null);
//        userDTO.setBranchId(savedUser.getBranch() != null ? savedUser.getBranch().getId() : null);

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
//        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setRole(userDTO.getRole());
        user.setRegisteredAt(userDTO.getRegisteredAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        user.setLastLoginAt(userDTO.getLastLoginAt());
        user.setProfileImageId(userDTO.getProfileImageId());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}