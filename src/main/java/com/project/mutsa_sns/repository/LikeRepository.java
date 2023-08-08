package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.LikeEntity;
import com.project.mutsa_sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByArticleAndUser(ArticleEntity article, UserEntity user);
}
