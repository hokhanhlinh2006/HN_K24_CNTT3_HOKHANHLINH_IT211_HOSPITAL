package com.hospital.model.entity;

import com.hospital.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    private String fullName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private Boolean isActive;
}