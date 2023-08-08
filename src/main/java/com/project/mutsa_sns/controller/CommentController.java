package com.project.mutsa_sns.controller;

import com.project.mutsa_sns.dto.CommentRequestDto;
import com.project.mutsa_sns.dto.ResponseDto;
import com.project.mutsa_sns.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<ResponseDto> createComment(
            @PathVariable Long articleId,
            @RequestBody CommentRequestDto requestDto
    ) {
        ResponseDto response = commentService.createComment(articleId, requestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto
    ) {
        ResponseDto response = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId) {
        ResponseDto response = commentService.deleteComment(commentId);
        return ResponseEntity.ok(response);
    }
}