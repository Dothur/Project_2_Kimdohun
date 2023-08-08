package com.project.mutsa_sns.controller;

import com.project.mutsa_sns.dto.ArticleDetailResponseDto;
import com.project.mutsa_sns.dto.ArticleListResponseDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 게시물 생성 엔드포인트
    @PostMapping
    public ResponseEntity<ResponseDto> createArticle(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile representativeImage,
            @RequestParam List<MultipartFile> images
    ) {
        ResponseDto response = articleService.createArticle(title, content, representativeImage, images);
        return ResponseEntity.ok(response);
    }

    // 게시물 목록 조회 엔드포인트
    @GetMapping
    public ResponseEntity<List<ArticleListResponseDto>> getArticleList() {
        List<ArticleListResponseDto> responseDtoList = articleService.getArticleList();
        return ResponseEntity.ok(responseDtoList);
    }

    // 게시물 상세 조회 엔드포인트
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDetailResponseDto> getArticleDetail(@PathVariable Long articleId) {
        ArticleDetailResponseDto responseDto = articleService.getArticleDetail(articleId);
        return ResponseEntity.ok(responseDto);
    }

    // 게시물 수정 엔드포인트
    @PutMapping("/{articleId}")
    public ResponseEntity<ResponseDto> updateArticle(
            @PathVariable Long articleId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile representativeImage,
            @RequestParam List<MultipartFile> images
    ) {
        ResponseDto response = articleService.updateArticle(articleId, title, content, representativeImage, images);
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제 엔드포인트
    @DeleteMapping("/{articleId}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable Long articleId) {
        ResponseDto response = articleService.deleteArticle(articleId);
        return ResponseEntity.ok(response);
    }
}
