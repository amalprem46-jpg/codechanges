package com.example.student_management.exception;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,String>> handleCustomException(CustomException ex){
        Map<String,String> error=new HashMap<>();
        error.put("errorCode",ex.getErrorCode());
        error.put("errorMessage",ex.getMessage());
        HttpStatus status=ex.getErrorCode().equals("USR203")?HttpStatus.NON_AUTHORITATIVE_INFORMATION:HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(error,status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String,String>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<Map<String,String>> errors=new ArrayList<>();
        for(FieldError f:ex.getBindingResult().getFieldErrors()){
            Map<String,String> map=new HashMap<>();
            map.put("errorField",f.getField());
            map.put("errorMessage",f.getDefaultMessage());
            map.put("errorCode","VAL400");
            errors.add(map);
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGeneralException(Exception ex){
        Map<String,String> error=new HashMap<>();
        error.put("errorCode","GEN500");
        error.put("errorMessage",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}