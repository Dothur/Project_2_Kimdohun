package com.project.mutsa_sns.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime deletedAt;
}
