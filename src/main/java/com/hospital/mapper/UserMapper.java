package com.hospital.mapper;

import com.hospital.model.dto.response.UserResponse;
import com.hospital.model.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(
            User user
    ) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .build();
    }
}