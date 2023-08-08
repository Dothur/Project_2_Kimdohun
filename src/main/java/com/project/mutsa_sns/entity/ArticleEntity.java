package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 피드 아이디

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 작성자 정보

    @Column(nullable = false)
    private String title; // 피드 제목

    @Column(nullable = false)
    private String content; // 피드 내용

    @Column
    private boolean draft; // 초안 여부

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 삭제 시간

    @OneToMany(mappedBy = "article")
    private List<ArticleImageEntity> articleImages = new ArrayList<>(); // 피드 이미지 리스트

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> comments = new ArrayList<>(); // 댓글 리스트

    @ManyToMany
    @JoinTable(
            name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> likes = new ArrayList<>(); // 좋아요한 사용자 리스트
}
