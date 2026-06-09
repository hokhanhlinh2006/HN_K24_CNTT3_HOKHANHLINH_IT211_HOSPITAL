package com.hospital.security.config;

import com.hospital.model.entity.User;
import com.hospital.model.enums.RoleEnum;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder
        implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        User admin = User.builder()
                .username("admin")
                .passwordHash(
                        passwordEncoder.encode(
                                "123456"))
                .fullName("System Admin")
                .email("admin@gmail.com")
                .role(RoleEnum.ADMIN)
                .isActive(true)
                .build();

        User doctor = User.builder()
                .username("doctor1")
                .passwordHash(
                        passwordEncoder.encode(
                                "123456"))
                .fullName("Doctor One")
                .email("doctor@gmail.com")
                .role(RoleEnum.DOCTOR)
                .isActive(true)
                .build();

        User patient = User.builder()
                .username("patient1")
                .passwordHash(
                        passwordEncoder.encode(
                                "123456"))
                .fullName("Patient One")
                .email("patient@gmail.com")
                .role(RoleEnum.PATIENT)
                .isActive(true)
                .build();

        userRepository.save(admin);
        userRepository.save(doctor);
        userRepository.save(patient);
    }
}