package com.project.mutsa_sns.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDetailResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private List<String> imageUrl;
}
