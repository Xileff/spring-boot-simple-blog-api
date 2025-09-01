package com.felix.simple_blog.entities.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class PostRequest {
    Long id;
    String title;
    String content;
}
