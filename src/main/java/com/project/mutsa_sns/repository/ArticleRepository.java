package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
