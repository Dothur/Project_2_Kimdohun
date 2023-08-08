package com.project.mutsa_sns.service;

import com.project.mutsa_sns.entity.UserEntity;
import com.project.mutsa_sns.jwt.JwtTokenUtils;
import com.project.mutsa_sns.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final HttpServletRequest request;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    public AuthService(HttpServletRequest request, JwtTokenUtils jwtTokenUtils, UserRepository userRepository) {
        this.request = request;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    public UserEntity getUser() {
        String token = extractTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (!jwtTokenUtils.validate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String username = jwtTokenUtils.parseClaims(token).getSubject();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return authHeader.split(" ")[1];
    }
}