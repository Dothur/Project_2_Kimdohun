package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private Long id;              // 아이디 (자동 생성 등의 경우)
    private String username;      // 사용자명
    private String password;      // 비밀번호
    private String passwordCheck; // 비밀번호 확인
    private String email;         // 이메일
    private String phone;         // 전화번호
}
