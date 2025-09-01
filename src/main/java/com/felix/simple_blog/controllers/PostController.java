package com.felix.simple_blog.controllers;

import com.felix.simple_blog.models.BaseResponse;
import com.felix.simple_blog.services.PostService;
import com.felix.simple_blog.entities.dto.PostRequest;
import com.felix.simple_blog.entities.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        final PostResponse post = postService.createPost(userEmail, postRequest);

        URI location = URI.create(String.format("/api/v1/posts/%d", post.getId()));

        return ResponseEntity.created(location).body(new BaseResponse<>("Success", post));
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
        final List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(new BaseResponse<>("Success", posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable Long id) {
        final PostResponse post = postService.getPostById(id);
        return ResponseEntity.ok(new BaseResponse<>("Success", post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        final PostResponse post = postService.updatePost(userEmail, id, postRequest);

        return ResponseEntity.ok(new BaseResponse<>("Success", post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> deletePost(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        final PostResponse post = postService.deletePost(userEmail, id);

        return ResponseEntity.ok(new BaseResponse<>("Success", post));
    }
}
