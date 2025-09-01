package com.felix.simple_blog.entities.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
}
