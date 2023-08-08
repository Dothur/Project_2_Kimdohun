package com.project.mutsa_sns.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username; // 사용자명
    private String password; // 비밀번호
}
