package com.hospital.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse {

    private String accessToken;

    private String refreshToken;

    private String username;

    private String role;
}