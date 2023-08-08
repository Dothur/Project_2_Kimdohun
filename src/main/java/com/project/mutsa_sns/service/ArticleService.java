package com.project.mutsa_sns.service;

import com.project.mutsa_sns.dto.ArticleDetailResponseDto;
import com.project.mutsa_sns.dto.ArticleListResponseDto;
import com.project.mutsa_sns.dto.CommentResponseDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.ArticleImageEntity;
import com.project.mutsa_sns.entity.CommentEntity;
import com.project.mutsa_sns.entity.UserEntity;
import com.project.mutsa_sns.repository.ArticleImageRepository;
import com.project.mutsa_sns.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final AuthService authService;

    // 피드 생성 메서드
    public ResponseDto createArticle(String title, String content,
                                     MultipartFile representativeImage, List<MultipartFile> images) {

        // 현재 로그인한 사용자 정보를 가져옴
        UserEntity userEntity = authService.getUser();

        // 피드 엔티티 생성 및 저장
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setUser(userEntity);
        articleEntity.setTitle(title);
        articleEntity.setContent(content);
        articleEntity.setDraft(false);

        articleRepository.save(articleEntity);

        // 대표 이미지 처리
        handleArticleImage(articleEntity, representativeImage, true);

        // 추가 이미지 처리
        for (MultipartFile image : images) {
            handleArticleImage(articleEntity, image, false);
        }

        return new ResponseDto("피드가 생성되었습니다.");
    }

    // 피드 목록 조회 기능
    public List<ArticleListResponseDto> getArticleList() {
        List<ArticleEntity> articles = articleRepository.findAll();
        List<ArticleListResponseDto> responseDtoList = new ArrayList<>();

        for (ArticleEntity article : articles) {
            ArticleListResponseDto responseDto = new ArticleListResponseDto();
            responseDto.setId(article.getId());
            responseDto.setUserId(article.getUser().getId());
            responseDto.setUsername(article.getUser().getUsername());
            responseDto.setTitle(article.getTitle());

            // 피드에 첨부된 이미지가 있다면 첫번째 이미지 URL을, 없다면 기본 이미지 URL을 설정
            List<ArticleImageEntity> images = article.getArticleImages();
            if (!images.isEmpty()) {
                responseDto.setImageUrl(images.get(0).getImageUrl());
            } else {
                responseDto.setImageUrl("/static/media/**");
            }

            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    // 피드 단독 조회 기능
    public ArticleDetailResponseDto getArticleDetail(Long articleId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ArticleDetailResponseDto responseDto = new ArticleDetailResponseDto();
        responseDto.setId(article.getId());
        responseDto.setUserId(article.getUser().getId());
        responseDto.setUsername(article.getUser().getUsername());
        responseDto.setTitle(article.getTitle());
        responseDto.setContent(article.getContent());
        responseDto.setDeletedAt(article.getDeletedAt());

        List<String> imageUrl = new ArrayList<>();
        for (ArticleImageEntity image : article.getArticleImages()) {
            imageUrl.add(image.getImageUrl());
        }
        responseDto.setImageUrl(imageUrl);

        // 댓글, 좋아요?
        // 댓글 정보 추가
        List<CommentResponseDto> commentResponseList = new ArrayList<>();
        for (CommentEntity comment : article.getComments()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setId(comment.getId());
            commentResponseDto.setUserId(comment.getUser().getId());
            commentResponseDto.setUsername(comment.getUser().getUsername());
            commentResponseDto.setContent(comment.getContent());
            commentResponseDto.setDeletedAt(comment.getDeletedAt());
            commentResponseList.add(commentResponseDto);
        }
        responseDto.setComments(commentResponseList);

        return responseDto;
    }

    // 피드 업데이트 메서드
    public ResponseDto updateArticle(Long articleId, String title, String content,
                                     MultipartFile representativeImage, List<MultipartFile> images) {

        UserEntity userEntity = authService.getUser();

        ArticleEntity articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!articleEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 피드를 수정할 권한이 없습니다.");
        }

        articleEntity.setTitle(title);
        articleEntity.setContent(content);

        articleRepository.save(articleEntity);

        // 기존 이미지들을 삭제
        List<ArticleImageEntity> existingImages = articleImageRepository.findByArticle(articleEntity);
        // 실제 파일도 삭제 처리할 수 있음
        articleImageRepository.deleteAll(existingImages);

        // 대표 이미지 및 추가 이미지 처리
        handleArticleImage(articleEntity, representativeImage, true);
        for (MultipartFile image : images) {
            handleArticleImage(articleEntity, image, false);
        }

        return new ResponseDto("피드가 업데이트되었습니다.");
    }

    // 피드 삭제 기능
    public ResponseDto deleteArticle(Long articleId) {
        UserEntity userEntity = authService.getUser();

        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!article.getUser().getId().equals(userEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시물을 삭제할 권한이 없습니다.");
        }

        // 게시물 삭제 대신 삭제되었다는 표시 (deletedAt 필드에 현재 시간 설정)
        article.setDeletedAt(LocalDateTime.now());
        article = articleRepository.save(article);
        log.info(String.valueOf(article.getDeletedAt()));

        return new ResponseDto("게시물이 삭제되었습니다.");
    }

    // 이미지 처리 메서드
    public void handleArticleImage(ArticleEntity articleEntity, MultipartFile image, boolean isRepresentative) {
        // 이미지를 저장할 디렉토리 경로 설정
        String imageDir = String.format("media/article/%d/", articleEntity.getId());

        try {
            // 이미지 저장 디렉토리 생성
            Files.createDirectories(Path.of(imageDir));
        } catch (IOException ex) {
            log.error("이미지 디렉토리 생성 실패: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 이미지 파일 이름 분리 및 확장자 추출
        String originalFilename = image.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String imageName = isRepresentative ? "representative_image." + extension : UUID.randomUUID().toString() + "." + extension;
        String imagePath = imageDir + imageName;

        log.info(imagePath);

        // 이미지 파일을 실제 경로에 저장
        try {
            image.transferTo(Path.of(imagePath));
        } catch (IOException ex) {
            log.error("이미지 저장 실패: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 피드 이미지 엔티티 생성 및 저장
        ArticleImageEntity articleImageEntity = new ArticleImageEntity();
        articleImageEntity.setImageUrl(String.format("/static/%d/%s", articleEntity.getId(), imageName));
        articleImageEntity.setArticle(articleEntity);

        articleImageRepository.save(articleImageEntity);
    }
}