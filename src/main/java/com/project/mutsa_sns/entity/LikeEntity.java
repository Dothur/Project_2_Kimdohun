package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좋아요 아이디

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article; // 좋아요가 속한 피드 정보

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // 좋아요를 누른 사용자 정보
}
