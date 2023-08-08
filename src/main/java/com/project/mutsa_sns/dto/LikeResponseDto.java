package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class LikeResponseDto {

    private boolean success;  // 성공 여부
    private String message;   // 결과 메시지

    // 생성자
    public LikeResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
