package com.felix.simple_blog.repositories;

import com.felix.simple_blog.entities.Comment;
import com.felix.simple_blog.entities.dto.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    @Query(value = "SELECT c.id AS id, " +
            "   c.content, " +
            "   c.createdAt, " +
            "   u.username " +
            "FROM Comment c JOIN c.user u " +
            "WHERE c.post.id = :postId")
    List<CommentResponse> findAllByPostIdWithUser(@Param("postId") Long postId);
}
