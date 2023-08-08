package com.project.mutsa_sns.repository;

import com.project.mutsa_sns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username); // 사용자명으로 사용자 정보 조회
    Boolean existsByUsername(String username); // 사용자명으로 사용자 존재 여부 확인
}
