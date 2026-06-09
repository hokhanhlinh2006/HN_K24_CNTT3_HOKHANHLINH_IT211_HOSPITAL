package com.hospital.repository;

import com.hospital.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUsername(
            String username
    );

    boolean existsByUsername(
            String username
    );

    boolean existsByEmail(
            String email
    );
    Page<User> findByUsernameContainingIgnoreCase(
            String username,
            Pageable pageable
    );
}