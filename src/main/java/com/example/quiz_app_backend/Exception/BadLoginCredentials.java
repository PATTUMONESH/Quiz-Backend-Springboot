package com.example.quiz_app_backend.Exception;

public class BadLoginCredentials extends RuntimeException{
    public BadLoginCredentials(String message){
        super(message);
    }
}
