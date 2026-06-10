package com.hospital.service;

import com.hospital.model.dto.request.RefreshTokenRequest;
import com.hospital.model.dto.request.UserLogin;
import com.hospital.model.dto.request.UserRegisterRequest;
import com.hospital.model.dto.response.JWTResponse;
import com.hospital.model.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(
            UserRegisterRequest request
    );

    JWTResponse login(
            UserLogin request
    );

    JWTResponse refreshToken(
            RefreshTokenRequest request
    );

    void logout(
            String token
    );
}