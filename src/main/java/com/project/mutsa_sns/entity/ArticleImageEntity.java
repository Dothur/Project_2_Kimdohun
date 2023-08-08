package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Article_Images")
public class ArticleImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 이미지 아이디

    @Column(nullable = false)
    private String imageUrl; // 이미지 URL

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article; // 해당 이미지가 속한 피드 정보
}
