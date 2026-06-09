package com.hospital.model.dto.response;

import com.hospital.model.enums.RoleEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phone;

    private RoleEnum role;

    private Boolean isActive;
}