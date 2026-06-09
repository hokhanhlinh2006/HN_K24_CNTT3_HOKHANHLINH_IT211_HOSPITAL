package com.hospital.service.impl;

import com.hospital.model.dto.request.RefreshTokenRequest;
import com.hospital.model.dto.request.UserLogin;
import com.hospital.model.dto.request.UserRegisterRequest;
import com.hospital.model.dto.response.JWTResponse;
import com.hospital.model.entity.RefreshToken;
import com.hospital.model.entity.TokenBlacklist;
import com.hospital.model.entity.User;
import com.hospital.model.enums.RoleEnum;
import com.hospital.repository.TokenBlacklistRepository;
import com.hospital.repository.UserRepository;
import com.hospital.security.jwt.JWTProvider;
import com.hospital.service.AuthService;
import com.hospital.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    private final AuthenticationManager
            authenticationManager;

    private final JWTProvider
            jwtProvider;

    private final RefreshTokenService
            refreshTokenService;

    private final TokenBlacklistRepository
            blacklistRepository;

    @Override
    public JWTResponse register(
            UserRegisterRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        User user = User.builder()
                .username(
                        request.getUsername())
                .passwordHash(
                        passwordEncoder.encode(
                                request.getPassword()))
                .fullName(
                        request.getFullName())
                .email(
                        request.getEmail())
                .phone(
                        request.getPhone())
                .role(RoleEnum.PATIENT)
                .isActive(true)
                .build();

        userRepository.save(user);

        String accessToken =
                jwtProvider
                        .generateAccessToken(
                                user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return JWTResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken())
                .username(
                        user.getUsername())
                .role(
                        user.getRole().name())
                .build();
    }

    @Override
    public JWTResponse login(
            UserLogin request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user =
                userRepository
                        .findByUsername(
                                request.getUsername())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        String accessToken =
                jwtProvider
                        .generateAccessToken(
                                user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return JWTResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken())
                .username(
                        user.getUsername())
                .role(
                        user.getRole().name())
                .build();
    }

    @Override
    public JWTResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService
                        .verifyToken(
                                request.getRefreshToken()
                        );

        String accessToken =
                jwtProvider
                        .generateAccessToken(
                                refreshToken
                                        .getUser()
                                        .getUsername()
                        );

        return JWTResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken())
                .username(
                        refreshToken
                                .getUser()
                                .getUsername())
                .role(
                        refreshToken
                                .getUser()
                                .getRole()
                                .name())
                .build();
    }

    @Override
    public void logout(
            String token) {

        TokenBlacklist blacklist =
                TokenBlacklist.builder()
                        .tokenString(token)
                        .revokedAt(
                                LocalDateTime.now())
                        .build();

        blacklistRepository
                .save(blacklist);
    }
}