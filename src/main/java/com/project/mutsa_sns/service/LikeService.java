package com.project.mutsa_sns.service;

import com.project.mutsa_sns.dto.LikeResponseDto;
import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.UserEntity;
import com.project.mutsa_sns.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ArticleRepository articleRepository;
    private final AuthService authService;

    // 피드 좋아요 토글 기능
    public LikeResponseDto toggleArticleLike(Long articleId) {
        // 피드 정보 가져오기
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 현재 로그인한 사용자 정보 가져오기
        UserEntity currentUser = authService.getUser();

        // 자신의 피드인 경우 좋아요 할 수 없음
        if (currentUser.getId().equals(article.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 피드는 좋아요를 할 수 없습니다.");
        }

        // 이미 좋아요를 했는지 확인
        boolean isLiked = article.getLikes().contains(currentUser);

        // 이미 좋아요를 했다면 좋아요 취소, 아니면 좋아요 추가
        if (isLiked) {
            article.getLikes().remove(currentUser);
        } else {
            article.getLikes().add(currentUser);
        }

        // 변경된 정보 저장
        articleRepository.save(article);

        // 응답 메시지 생성
        String message = isLiked ? "좋아요가 취소되었습니다." : "좋아요가 추가되었습니다.";
        return new LikeResponseDto(true, message);
    }
}
