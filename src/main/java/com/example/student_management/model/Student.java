package com.example.student_management.model;

import jakarta.validation.constraints.*;

public class Student {
    private Long id;

    @NotBlank(message="Name cannot be empty")
    private String name;

    @Email(message="Email must be valid")
    @NotBlank(message="Email required")
    private String email;

    @Min(value=1,message="Age must be positive")
    private int age;

    public Student() {}
    public Student(Long id,String name,String email,int age){
        this.id=id; this.name=name; this.email=email; this.age=age;
    }

    // getters & setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public int getAge(){return age;}
    public void setAge(int age){this.age=age;}
}