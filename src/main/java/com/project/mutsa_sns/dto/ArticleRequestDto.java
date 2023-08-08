package com.project.mutsa_sns.dto;

import lombok.Data;

@Data
public class ArticleRequestDto {
    private String title;   // 게시물 제목
    private String content; // 게시물 내용
}
