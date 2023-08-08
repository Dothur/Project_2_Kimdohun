package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.ArticleImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImageRepository extends JpaRepository<ArticleImageEntity, Long> {

    List<ArticleImageEntity> findByArticle(ArticleEntity article);

}
