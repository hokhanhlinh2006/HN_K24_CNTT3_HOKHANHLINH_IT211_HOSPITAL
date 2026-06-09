package com.hospital.service;

import com.hospital.model.entity.RefreshToken;
import com.hospital.model.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(
            User user
    );

    RefreshToken verifyToken(
            String token
    );

    void deleteToken(
            String token
    );
}