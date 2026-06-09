package com.hospital.service.impl;

import com.hospital.mapper.UserMapper;
import com.hospital.model.dto.request.CreateUserRequest;
import com.hospital.model.dto.request.UpdateUserRequest;
import com.hospital.model.dto.response.UserResponse;
import com.hospital.model.entity.User;
import com.hospital.repository.UserRepository;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse create(
            CreateUserRequest request
    ) {

        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .isActive(true)
                .build();

        return UserMapper.toResponse(
                userRepository.save(user)
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse update(
            Long id,
            UpdateUserRequest request
    ) {

        User user =
                userRepository.findById(id)
                        .orElseThrow();

        user.setFullName(
                request.getFullName());

        user.setEmail(
                request.getEmail());

        user.setPhone(
                request.getPhone());

        user.setRole(
                request.getRole());

        user.setIsActive(
                request.getIsActive());

        return UserMapper.toResponse(
                userRepository.save(user)
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findById(
            Long id
    ) {

        return UserMapper.toResponse(
                userRepository
                        .findById(id)
                        .orElseThrow()
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(
            Long id
    ) {

        userRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> getAll(
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        return userRepository
                .findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> search(
            String keyword,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        return userRepository
                .findByUsernameContainingIgnoreCase(
                        keyword,
                        pageable
                )
                .map(UserMapper::toResponse);
    }
}