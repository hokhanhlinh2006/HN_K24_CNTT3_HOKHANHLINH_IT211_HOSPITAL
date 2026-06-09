package com.hospital.model.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phone;
}