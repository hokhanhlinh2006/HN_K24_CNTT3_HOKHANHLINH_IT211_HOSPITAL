package com.hospital.model.dto.request;

import com.hospital.model.enums.RoleEnum;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phone;

    private RoleEnum role;
}