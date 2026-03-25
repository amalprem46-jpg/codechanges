package com.example.student_management.model;

public class Enrollment {
    private Long id;
    private Long studentId;
    private Long courseId;
    private int mark;

    public Enrollment() {}
    public Enrollment(Long id, Long studentId, Long courseId,int mark){
        this.id=id; this.studentId=studentId; this.courseId=courseId; this.mark=mark;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Long getStudentId(){return studentId;}
    public void setStudentId(Long studentId){this.studentId=studentId;}
    public Long getCourseId(){return courseId;}
    public void setCourseId(Long courseId){this.courseId=courseId;}
    public int getMark(){return mark;}
    public void setMark(int mark){this.mark=mark;}
}