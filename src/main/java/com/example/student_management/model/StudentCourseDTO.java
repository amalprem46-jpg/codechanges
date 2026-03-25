package com.example.student_management.model;


public class StudentCourseDTO {
    private String studentName;
    private String courseName;
    private int mark;

    public StudentCourseDTO(String studentName,String courseName,int mark){
        this.studentName=studentName;
        this.courseName=courseName;
        this.mark=mark;
    }

    public String getStudentName(){return studentName;}
    public String getCourseName(){return courseName;}
    public int getMark(){return mark;}
}