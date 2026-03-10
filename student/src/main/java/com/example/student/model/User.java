package com.example.student.model;

import jakarta.validation.constraints.*;

public class User {

    private Long id;

    @Email(message = "Username must be email")
    @NotBlank(message = "Email cannot be empty")
    private String username;

    @Size(min = 8, message = "Password must contain 8 characters")
    private String password;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}