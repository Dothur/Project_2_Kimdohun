package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class LikeResponseDto {

    private boolean success;
    private String message;

    public LikeResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
