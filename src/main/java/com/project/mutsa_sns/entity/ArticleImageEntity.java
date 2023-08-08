package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Article_Images")
public class ArticleImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article;
}
