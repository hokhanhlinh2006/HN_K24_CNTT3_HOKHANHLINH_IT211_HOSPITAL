package com.hospital.security.config;

import com.hospital.security.jwt.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.
        AuthenticationManager;
import org.springframework.security.config.annotation.
        authentication.configuration.
        AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.
        EnableMethodSecurity;
import org.springframework.security.config.annotation.web.
        builders.HttpSecurity;
import org.springframework.security.config.http.
        SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.
        BCryptPasswordEncoder;
import org.springframework.security.crypto.password.
        PasswordEncoder;
import org.springframework.security.web.
        SecurityFilterChain;
import org.springframework.security.web.authentication.
        UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JWTFilter jwtFilter;

    private final JWTAuthenticationEntryPoint
            authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain
    securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )

                .exceptionHandling(
                        ex ->
                                ex.authenticationEntryPoint(
                                        authenticationEntryPoint
                                )
                )

                .authorizeHttpRequests(auth ->
                        auth

                                .requestMatchers(
                                        "/api/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/api/admin/**"
                                )
                                .hasRole("ADMIN")

                                .requestMatchers(
                                        "/api/doctor/**"
                                )
                                .hasRole("DOCTOR")

                                .requestMatchers(
                                        "/api/patient/**"
                                )
                                .hasRole("PATIENT")

                                .anyRequest()
                                .authenticated()
                );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}