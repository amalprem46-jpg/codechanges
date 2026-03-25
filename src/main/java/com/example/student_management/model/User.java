package com.example.student_management.model;


import jakarta.validation.constraints.*;

public class User {
    private Long id;

    @Email(message="Username must be email")
    @NotBlank(message="Username required")
    private String username;

    @Size(min=8,message="Password must be 8+ characters")
    @NotBlank(message="Password required")
    private String password;

    public User() {}
    public User(Long id,String username,String password){
        this.id=id; this.username=username; this.password=password;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}