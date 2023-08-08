package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
