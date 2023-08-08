package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findAllByUser(UserEntity user);

    List<ArticleEntity> findAllByDraftFalse();

    List<ArticleEntity> findAllByUserAndDraftFalse(UserEntity user);

}
