package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class ArticleListResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String imageUrl;
}
