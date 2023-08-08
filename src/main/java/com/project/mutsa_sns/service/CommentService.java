package com.project.mutsa_sns.service;

import com.project.mutsa_sns.dto.CommentRequestDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.entity.ArticleEntity;
import com.project.mutsa_sns.entity.CommentEntity;
import com.project.mutsa_sns.entity.UserEntity;
import com.project.mutsa_sns.repository.ArticleRepository;
import com.project.mutsa_sns.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final ArticleRepository articleRepository; // ArticleRepository 추가

    public CommentService(CommentRepository commentRepository, AuthService authService, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.articleRepository = articleRepository; // ArticleRepository 초기화
    }

    // 댓글 작성
    public ResponseDto createComment(Long articleId, CommentRequestDto requestDto) {
        // 현재 로그인한 사용자 정보 가져오기
        UserEntity userEntity = authService.getUser();

        // 해당 ArticleEntity 가져오기
        ArticleEntity articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 Article을 찾을 수 없습니다."));

        // 댓글 엔티티 생성 및 저장
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUser(userEntity);
        commentEntity.setArticle(articleEntity);
        commentEntity.setContent(requestDto.getContent());

        commentRepository.save(commentEntity);

        return new ResponseDto("댓글이 작성되었습니다.");
    }

    // 댓글 수정
    public ResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
        // 현재 로그인한 사용자 정보 가져오기
        UserEntity userEntity = authService.getUser();

        // 댓글 조회
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 댓글을 찾을 수 없습니다."));

        // 작성자 확인 및 수정
        if (!commentEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글을 수정할 권한이 없습니다.");
        }

        commentEntity.setContent(requestDto.getContent());
        commentRepository.save(commentEntity);

        return new ResponseDto("댓글이 수정되었습니다.");
    }

    // 댓글 삭제
    public ResponseDto deleteComment(Long commentId) {
        // 현재 로그인한 사용자 정보 가져오기
        UserEntity userEntity = authService.getUser();

        // 댓글 조회
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 댓글을 찾을 수 없습니다."));

        // 작성자 확인 및 삭제 표시
        if (!commentEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글을 삭제할 권한이 없습니다.");
        }

        commentEntity.setDeletedAt(LocalDateTime.now());
        commentRepository.save(commentEntity);

        return new ResponseDto("댓글이 삭제되었습니다.");
    }
}
