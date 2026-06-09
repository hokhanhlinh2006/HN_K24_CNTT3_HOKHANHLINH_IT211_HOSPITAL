package com.hospital.service;

import com.hospital.model.dto.request.UserLogin;
import com.hospital.model.dto.response.JWTResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void loginSuccess() {

        UserLogin request =
                new UserLogin();

        request.setUsername("admin");
        request.setPassword("123456");

        JWTResponse response =
                authService.login(request);

        assertNotNull(
                response.getAccessToken()
        );
    }
}