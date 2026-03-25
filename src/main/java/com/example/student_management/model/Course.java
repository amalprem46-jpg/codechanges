package com.example.student_management.model;

public class Course {
    private Long id;
    private String courseName;

    public Course() {}
    public Course(Long id,String courseName){this.id=id; this.courseName=courseName;}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getCourseName(){return courseName;}
    public void setCourseName(String courseName){this.courseName=courseName;}
}