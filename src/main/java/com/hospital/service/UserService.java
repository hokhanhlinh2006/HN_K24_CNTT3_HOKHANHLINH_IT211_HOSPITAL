package com.hospital.service;

import com.hospital.model.dto.request.CreateUserRequest;
import com.hospital.model.dto.request.UpdateUserRequest;
import com.hospital.model.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse create(
            CreateUserRequest request
    );

    UserResponse update(
            Long id,
            UpdateUserRequest request
    );

    UserResponse findById(
            Long id
    );

    void delete(
            Long id
    );

    Page<UserResponse> getAll(
            int page,
            int size
    );

    Page<UserResponse> search(
            String keyword,
            int page,
            int size
    );
}