package com.felix.simple_blog.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post with id " + id.toString() + "not found");
    }
}
