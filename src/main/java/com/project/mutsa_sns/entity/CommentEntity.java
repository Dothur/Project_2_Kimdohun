package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 아이디

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article; // 댓글이 속한 피드 정보

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // 댓글 작성자 정보

    @Column(nullable = false)
    private String content; // 댓글 내용

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 댓글 삭제 일시
}
