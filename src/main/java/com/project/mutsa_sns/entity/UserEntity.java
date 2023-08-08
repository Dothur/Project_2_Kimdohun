package com.project.mutsa_sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // 사용자의 ID

    @Column(nullable = false, unique = true)
    private String username;     // 사용자의 이름 (아이디)

    @Column(nullable = false)
    private String password;     // 사용자의 암호화된 비밀번호

    private String profile_img;  // 사용자의 프로필 이미지 경로

    private String email;        // 사용자의 이메일

    private String phone;        // 사용자의 전화번호
}
