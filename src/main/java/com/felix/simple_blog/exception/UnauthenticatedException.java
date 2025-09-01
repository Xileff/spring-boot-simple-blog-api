package com.felix.simple_blog.exception;

public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException() {
        super("You are not authenticated");
    }
}
