package com.trisys.Pos.System.payload.dto;

import com.trisys.Pos.System.domain.UserRole;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Gender gender;

    private LocalDate dateOfBirth;

    private UserRole role;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    private String profileImageId;

    private String password;

    private Long branchId;

    private Long storeId;
}