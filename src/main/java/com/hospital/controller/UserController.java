package com.hospital.controller;

import com.hospital.model.dto.request.CreateUserRequest;
import com.hospital.model.dto.request.UpdateUserRequest;
import com.hospital.model.dto.response.ApiDataResponse;
import com.hospital.model.dto.response.UserResponse;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiDataResponse<UserResponse> create(
            @RequestBody CreateUserRequest request
    ) {

        return ApiDataResponse
                .<UserResponse>builder()
                .success(true)
                .message("Create user success")
                .data(userService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiDataResponse<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {

        return ApiDataResponse
                .<UserResponse>builder()
                .success(true)
                .message("Update success")
                .data(
                        userService.update(
                                id,
                                request
                        )
                )
                .build();
    }

    @GetMapping("/{id}")
    public ApiDataResponse<UserResponse> detail(
            @PathVariable Long id
    ) {

        return ApiDataResponse
                .<UserResponse>builder()
                .success(true)
                .message("User detail")
                .data(
                        userService.findById(id)
                )
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiDataResponse<String> delete(
            @PathVariable Long id
    ) {

        userService.delete(id);

        return ApiDataResponse
                .<String>builder()
                .success(true)
                .message("Delete success")
                .data("OK")
                .build();
    }

    @GetMapping
    public ApiDataResponse<Page<UserResponse>>
    getAll(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ApiDataResponse
                .<Page<UserResponse>>builder()
                .success(true)
                .message("User list")
                .data(
                        userService.getAll(
                                page,
                                size
                        )
                )
                .build();
    }

    @GetMapping("/search")
    public ApiDataResponse<Page<UserResponse>>
    search(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ApiDataResponse
                .<Page<UserResponse>>builder()
                .success(true)
                .message("Search result")
                .data(
                        userService.search(
                                keyword,
                                page,
                                size
                        )
                )
                .build();
    }
}