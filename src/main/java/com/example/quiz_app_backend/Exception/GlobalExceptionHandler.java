package com.example.quiz_app_backend.Exception;
import com.example.quiz_app_backend.Dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class
GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
       // Create a custom error response
        Response createdResponse = new Response(HttpStatus.CREATED.value(), e.getMessage());
        return new ResponseEntity<>(createdResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        // Create a custom error response
        Response errorResponse = new Response(HttpStatus.CONFLICT.value(),e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = BadLoginCredentials.class)
    public ResponseEntity<Object> handleBadLoginCredentials(BadLoginCredentials badLogin){
        Response loginError=new Response(HttpStatus.BAD_REQUEST.value(),badLogin.getMessage());
        return new ResponseEntity<>(loginError,HttpStatus.BAD_REQUEST);
    }




}

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleLoginException(Exception e) {
//        // Create a custom error response
//        Response loginResponse = new Response(HttpStatus.CREATED.value(), e.getMessage());
//        return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
//    }
