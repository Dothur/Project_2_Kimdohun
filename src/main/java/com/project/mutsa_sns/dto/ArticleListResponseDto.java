package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class ArticleListResponseDto {
    private Long id;         // 게시물 ID
    private Long userId;     // 작성자 사용자 ID
    private String username; // 작성자 사용자명
    private String title;    // 게시물 제목
    private String imageUrl; // 대표 이미지 URL
}
