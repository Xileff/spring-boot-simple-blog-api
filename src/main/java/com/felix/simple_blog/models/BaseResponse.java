package com.felix.simple_blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    final String message;
    T data;

    public BaseResponse(String message) {
        this.message = message;
    }
}
