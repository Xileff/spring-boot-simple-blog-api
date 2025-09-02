package com.felix.simple_blog.services;

import com.felix.simple_blog.entities.Comment;
import com.felix.simple_blog.entities.Post;
import com.felix.simple_blog.entities.User;
import com.felix.simple_blog.entities.dto.CommentRequest;
import com.felix.simple_blog.entities.dto.CommentResponse;
import com.felix.simple_blog.exception.ResourceNotFoundException;
import com.felix.simple_blog.repositories.CommentRepository;
import com.felix.simple_blog.repositories.PostRepository;
import com.felix.simple_blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponse createComment(Long postId, String userEmail, CommentRequest commentRequest) {
        final User user = this.findUserByEmail(userEmail);
        final Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new ResourceNotFoundException("Post with id " + postId + " not found");
        }

        final Comment comment = commentRepository.save(
                Comment.builder()
                    .content(commentRequest.getContent())
                    .post(post.get())
                    .user(user)
                    .build()
        );

        return toCommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostIdWithUser(postId);
    }

    private CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser().getName()
        );
    }

    private User findUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User " + email + " not found");
        }

        return userOpt.get();
    }
}
