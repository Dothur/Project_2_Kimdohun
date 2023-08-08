package com.project.mutsa_sns.controller;

import com.project.mutsa_sns.dto.LoginRequestDto;
import com.project.mutsa_sns.dto.RegisterRequestDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.jwt.JwtTokenDto;
import com.project.mutsa_sns.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        JwtTokenDto jwtTokenDto = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(jwtTokenDto);
    }

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        // 입력한 패스워드와 패스워드 확인이 일치하지 않으면 예외 발생
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordCheck())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "패스워드와 패스워드 확인이 일치하지 않습니다");
        }
        ResponseDto responseDto = userService.registerUser(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 프로필 이미지 업데이트 엔드포인트
    @PutMapping(value = "/update-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> userUpdateImage(
            @RequestParam(value = "image") MultipartFile multipartFile
    ) {
        // 이미지 업로드 서비스 호출
        ResponseDto responseDto = userService.uploadProfileImage(multipartFile);

        return ResponseEntity.ok(responseDto);
    }
}
