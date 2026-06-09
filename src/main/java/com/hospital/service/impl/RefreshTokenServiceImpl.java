package com.hospital.service.impl;

import com.hospital.model.entity.RefreshToken;
import com.hospital.model.entity.User;
import com.hospital.repository.RefreshTokenRepository;
import com.hospital.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository
            refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(
            User user) {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token(
                                UUID.randomUUID()
                                        .toString())
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7))
                        .user(user)
                        .build();

        return refreshTokenRepository
                .save(refreshToken);
    }

    @Override
    public RefreshToken verifyToken(
            String token) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Refresh token not found"
                                )
                        );

        if (refreshToken
                .getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Refresh token expired"
            );
        }

        return refreshToken;
    }

    @Override
    public void deleteToken(
            String token) {

        refreshTokenRepository
                .deleteByToken(token);
    }
}