package com.example.student.exception;

import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(e -> errors.add(new ErrorResponse("VAL001", e.getField(), e.getDefaultMessage())));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleUserNotFound(UserNotFoundException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse("USR404", "global", ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorResponse>> handleGeneric(Exception ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse("GEN500", "global", "Something went wrong: "+ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}