package com.project.mutsa_sns.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;            // 댓글 ID
    private Long userId;        // 작성자의 사용자 ID
    private String username;    // 작성자의 사용자 이름
    private String content;     // 댓글 내용
    private LocalDateTime deletedAt; // 삭제된 시간
}
