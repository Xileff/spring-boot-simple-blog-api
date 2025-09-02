package com.felix.simple_blog.controllers;

import com.felix.simple_blog.entities.dto.CommentRequest;
import com.felix.simple_blog.entities.dto.CommentResponse;
import com.felix.simple_blog.models.BaseResponse;
import com.felix.simple_blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponse<CommentResponse>> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        final CommentResponse commentResponse = commentService.createComment(postId, userEmail, commentRequest);

        return ResponseEntity
                .created(URI.create(String.format("/api/v1/posts/%d/comments", postId)))
                .body(new BaseResponse<>("Success", commentResponse));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByPostId(
            @PathVariable Long postId
    ) {
        final List<CommentResponse> commentResponseList = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(new BaseResponse<>("Success", commentResponseList));
    }
}
