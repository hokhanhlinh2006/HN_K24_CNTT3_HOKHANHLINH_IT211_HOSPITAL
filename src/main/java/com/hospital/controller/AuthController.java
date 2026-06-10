package com.hospital.controller;

import com.hospital.model.dto.request.RefreshTokenRequest;
import com.hospital.model.dto.request.UserLogin;
import com.hospital.model.dto.request.UserRegisterRequest;
import com.hospital.model.dto.response.ApiDataResponse;
import com.hospital.model.dto.response.JWTResponse;
import com.hospital.model.dto.response.UserResponse;
import com.hospital.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiDataResponse<UserResponse> register(
            @RequestBody UserRegisterRequest request
    ) {

        System.out.println("REGISTER API CALLED");

        return ApiDataResponse.<UserResponse>builder()
                .success(true)
                .message("Register success")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiDataResponse<JWTResponse> login(
            @RequestBody UserLogin request
    ) {

        System.out.println("LOGIN API CALLED");

        return ApiDataResponse.<JWTResponse>builder()
                .success(true)
                .message("Login success")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/refresh")
    public ApiDataResponse<JWTResponse> refresh(
            @RequestBody RefreshTokenRequest request
    ) {

        System.out.println("REFRESH API CALLED");

        return ApiDataResponse.<JWTResponse>builder()
                .success(true)
                .message("Refresh token success")
                .data(authService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiDataResponse<String> logout(
            @RequestHeader("Authorization")
            String authorization
    ) {

        System.out.println("LOGOUT API CALLED");

        String token = authorization.substring(7);

        authService.logout(token);

        return ApiDataResponse.<String>builder()
                .success(true)
                .message("Logout success")
                .data("OK")
                .build();
    }
}