package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username; // 사용자명
    private String password; // 비밀번호
}
