package com.project.mutsa_sns.service;

import com.project.mutsa_sns.detail.CustomUserDetails;
import com.project.mutsa_sns.detail.JpaUserDetailsManager;
import com.project.mutsa_sns.dto.LoginRequestDto;
import com.project.mutsa_sns.dto.RegisterRequestDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.entity.UserEntity;
import com.project.mutsa_sns.jwt.JwtTokenDto;
import com.project.mutsa_sns.jwt.JwtTokenUtils;
import com.project.mutsa_sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsManager manager;
    private final AuthService authService;

    public JwtTokenDto loginUser(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();

        if (!manager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "올바른 Username 이 아닙니다");
        }

        UserDetails userDetails = manager.loadUserByUsername(username);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password 가 일치하지 않습니다");
        }

        JwtTokenDto tokenDto = new JwtTokenDto();
        tokenDto.setToken(jwtTokenUtils.generateToken(userDetails));
        return tokenDto;
    }

    public ResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        String username = registerRequestDto.getUsername();
        String password = registerRequestDto.getPassword();

        if (manager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username 이 이미 존재합니다");
        }

        CustomUserDetails user = CustomUserDetails.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(registerRequestDto.getEmail())
                .phone(registerRequestDto.getPhone())
                .build();

        manager.createUser(user);

        return new ResponseDto("회원가입을 성공했습니다");
    }

    public ResponseDto uploadProfileImage(MultipartFile multipartFile) {
        UserEntity userEntity = authService.getUser();

        String profileDir = String.format("media/%d/", userEntity.getId());

        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String profileFilename = "image." + extension;
        String profilePath = profileDir + profileFilename;

        try {
            multipartFile.transferTo(Path.of(profilePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userEntity.setProfile_img(profilePath);
        userRepository.save(userEntity);

        return new ResponseDto("이미지가 등록되었습니다.");
    }
}