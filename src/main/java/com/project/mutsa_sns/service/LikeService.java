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

    public LikeResponseDto toggleArticleLike(Long articleId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserEntity currentUser = authService.getUser();

        if (currentUser.getId().equals(article.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 피드는 좋아요를 할 수 없습니다.");
        }

        boolean isLiked = article.getLikes().contains(currentUser);

        if (isLiked) {
            article.getLikes().remove(currentUser);
        } else {
            article.getLikes().add(currentUser);
        }

        articleRepository.save(article);

        String message = isLiked ? "좋아요가 취소되었습니다." : "좋아요가 추가되었습니다.";
        return new LikeResponseDto(true, message);
    }
}
