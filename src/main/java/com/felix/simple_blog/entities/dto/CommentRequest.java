package com.felix.simple_blog.entities.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentRequest {
    private final String content;
}
