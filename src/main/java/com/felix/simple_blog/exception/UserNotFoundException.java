package com.felix.simple_blog.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userEmail) {
        super("User " + userEmail + " not found!");
    }
}
