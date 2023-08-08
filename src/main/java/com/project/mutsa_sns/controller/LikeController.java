package com.project.mutsa_sns.controller;

import com.project.mutsa_sns.dto.LikeResponseDto;
import com.project.mutsa_sns.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 특정 게시물에 좋아요 토글 엔드포인트
    @PostMapping("/articles/{articleId}")
    public LikeResponseDto likeArticle(@PathVariable Long articleId) {
        return likeService.toggleArticleLike(articleId);
    }
}
