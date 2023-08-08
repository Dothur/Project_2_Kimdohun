package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String message; // API 응답 메시지를 저장하는 클래스

    public ResponseDto(String message) {
        this.message = message; // 응답 메시지를 받아 초기화하는 생성자
    }
}
