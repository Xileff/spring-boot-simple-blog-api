package com.felix.simple_blog.services;

import com.felix.simple_blog.entities.Post;
import com.felix.simple_blog.entities.User;
import com.felix.simple_blog.exception.ResourceNotFoundException;
import com.felix.simple_blog.repositories.PostRepository;
import com.felix.simple_blog.repositories.UserRepository;
import com.felix.simple_blog.entities.dto.PostRequest;
import com.felix.simple_blog.entities.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(String userEmail, PostRequest postRequest) {
        final User user = this.findUserByEmail(userEmail);

        Post post = postRepository.save(
                Post.builder()
                        .user(user)
                        .title(postRequest.getTitle())
                        .content(postRequest.getContent())
                        .build()
        );

        return toPostResponse(post);
    }

    public PostResponse updatePost(String userEmail, Long id, PostRequest postRequest) {
        final User user = this.findUserByEmail(userEmail);
        final Post post = this.findByIdAndUserEmail(id, user.getEmail());

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return toPostResponse(postRepository.save(post));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::toPostResponse).toList();
    }

    public PostResponse getPostById(Long id) {
        final Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            throw new ResourceNotFoundException("Post with id " + id + " not found");
        }

        return toPostResponse(postOpt.get());
    }

    public PostResponse deletePost(String userEmail, Long id) {
        final User user = this.findUserByEmail(userEmail);
        final Post post = this.findByIdAndUserEmail(id, user.getEmail());

        postRepository.delete(post);

        return toPostResponse(post);
    }

    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    private User findUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User " + email + " not found");
        }

        return userOpt.get();
    }

    private Post findByIdAndUserEmail (Long postId, String userEmail) {
        final Optional<Post> post = this.postRepository.findByIdAndUserEmail(postId, userEmail);

        if (post.isEmpty()) {
            throw new ResourceNotFoundException("Post with id " + postId + " not found");
        }

        return post.get();
    }
}
