package com.hospital.security.jwt;

import com.hospital.repository.TokenBlacklistRepository;
import com.hospital.security.principal.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    private final CustomUserDetailsService userDetailsService;

    private final TokenBlacklistRepository blacklistRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println(
                "JWT FILTER -> " + request.getMethod()
                        + " " + request.getRequestURI()
        );

        String header =
                request.getHeader("Authorization");

        if (header == null ||
                !header.startsWith("Bearer ")) {

            System.out.println(
                    "JWT FILTER -> No Authorization Header"
            );

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String token =
                header.substring(7);

        if (blacklistRepository
                .existsByTokenString(token)) {

            System.out.println(
                    "JWT FILTER -> Token Revoked"
            );

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token revoked"
            );

            return;
        }

        if (jwtProvider.validateToken(token)) {

            String username =
                    jwtProvider.extractUsername(
                            token
                    );

            System.out.println(
                    "JWT FILTER -> Authenticated User: "
                            + username
            );

            var userDetails =
                    userDetailsService
                            .loadUserByUsername(
                                    username
                            );

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            auth.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}