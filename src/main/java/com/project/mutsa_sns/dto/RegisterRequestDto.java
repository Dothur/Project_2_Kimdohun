package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private Long id;
    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String phone;
}
