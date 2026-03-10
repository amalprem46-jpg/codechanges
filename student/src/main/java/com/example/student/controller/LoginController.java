package com.example.student.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.student.model.User;
import com.example.student.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public String login(@Valid @RequestBody User user) {
    	
        return service.login(user.getUsername(), user.getPassword());
    }
}