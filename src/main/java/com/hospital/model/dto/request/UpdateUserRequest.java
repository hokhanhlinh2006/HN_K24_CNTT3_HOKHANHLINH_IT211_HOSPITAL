package com.hospital.model.dto.request;

import com.hospital.model.enums.RoleEnum;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String fullName;

    private String email;

    private String phone;

    private RoleEnum role;

    private Boolean isActive;
}