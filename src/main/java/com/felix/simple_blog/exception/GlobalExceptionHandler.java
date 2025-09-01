package com.felix.simple_blog.exception;

import com.felix.simple_blog.models.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public BaseResponse<Void> handleUnauthenticatedException(UnauthenticatedException ex) {
        return new BaseResponse<>(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public BaseResponse<Void> handlePostNotFoundException(PostNotFoundException ex) {
        return new BaseResponse<>(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public BaseResponse<Void> handleUserNotFoundException(UserNotFoundException ex) {
        return new BaseResponse<>(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<Void> handleInternalServerError(RuntimeException ex) {
        ex.printStackTrace();
        return new BaseResponse<>("Terjadi kesalahan pada server");
    }
}
