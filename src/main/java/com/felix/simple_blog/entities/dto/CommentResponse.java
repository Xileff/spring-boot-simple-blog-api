package com.felix.simple_blog.entities.dto;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String content, LocalDateTime createdAt, String username) {}
