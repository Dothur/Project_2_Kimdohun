package com.project.mutsa_sns.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDetailResponseDto {
    private Long id;                // 게시물 ID
    private Long userId;            // 작성자 사용자 ID
    private String username;        // 작성자 사용자명
    private String title;           // 게시물 제목
    private String content;         // 게시물 내용
    private LocalDateTime deletedAt;// 게시물 삭제 일시
    private List<String> imageUrl;  // 게시물 이미지 URL 목록
    private List<CommentResponseDto> comments; // 댓글 목록
}
