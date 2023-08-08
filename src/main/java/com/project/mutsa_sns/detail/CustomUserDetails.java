package com.project.mutsa_sns.detail;

import com.project.mutsa_sns.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
    UserDetails 인터페이스를 구현한 사용자 정보 클래스
    Spring Security 에서 사용자의 인증과 권한을 다루기 위해
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;             // 사용자의 ID
    private String username;     // 사용자의 이름 (아이디)
    private String password;     // 사용자의 암호화된 비밀번호
    private String profile_img;  // 사용자의 프로필 이미지 경로
    private String email;        // 사용자의 이메일
    private String phone;        // 사용자의 전화번호

    // 사용자의 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 이름(아이디)을 반환하는 메서드
    @Override
    public String getUsername() {
        return this.username;
    }

    // 사용자의 암호화된 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return id;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // 사용자의 계정이 만료되지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자의 계정이 잠기지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 인증 정보가 만료되지 않았음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자의 계정이 활성화되었음을 나타내는 메서드
    // true 로 변경하여 체크를 사용하지 않도록 함
    @Override
    public boolean isEnabled() {
        return true;
    }

    // CustomUserDetails 객체를 UserEntity 객체로 변환하는 메서드
    public static CustomUserDetails fromEntity(UserEntity entity) {
        CustomUserDetails details = new CustomUserDetails();
        details.id = entity.getId();
        details.username = entity.getUsername();
        details.password = entity.getPassword();
        details.profile_img = entity.getProfile_img();
        details.email = entity.getEmail();
        details.phone = entity.getPhone();
        return details;
    }

    // CustomUserDetails 객체를 UserEntity 객체로 변환하는 메서드
    public UserEntity newEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setProfile_img(profile_img);
        entity.setEmail(email);
        entity.setPhone(phone);
        return entity;
    }

    // CustomUserDetails 객체의 문자열 표현을 반환하는 메서드
    // 디버깅 or 로깅
    @Override
    public String toString() {
        return "CustomUserDetails{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", profile_img='" + profile_img + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               '}';
    }
}
