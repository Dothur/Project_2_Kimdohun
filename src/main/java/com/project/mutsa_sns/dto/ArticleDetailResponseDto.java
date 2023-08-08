package com.project.mutsa_sns.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDetailResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private LocalDateTime deletedAt;
    private List<String> imageUrl;
    private List<CommentResponseDto> comments;
}
