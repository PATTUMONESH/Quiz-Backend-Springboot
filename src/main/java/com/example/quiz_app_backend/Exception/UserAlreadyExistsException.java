package com.example.quiz_app_backend.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
