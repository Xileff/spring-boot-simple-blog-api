package com.felix.simple_blog.repositories;

import com.felix.simple_blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_Email(String email);

    @Query("SELECT p FROM Post p WHERE p.user.email = :email")
    List<Post> findPostsByUserEmail(@Param("email") String email);

    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.user.email = :email")
    Optional<Post> findByIdAndUserEmail(@Param("id") Long id, @Param("email") String email);
}
