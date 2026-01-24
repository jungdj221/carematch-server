package com.corp.carematch_server.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){

        System.out.println("error detected: " + e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(e.getMessage());
    }
}
