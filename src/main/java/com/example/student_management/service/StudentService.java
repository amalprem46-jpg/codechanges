package com.example.student_management.service;
import com.example.student_management.model.Student;
import com.example.student_management.model.StudentCourseDTO;
import java.util.List;

public interface StudentService {
    int save(Student s);
    List<Student> findAll();
    Student findById(Long id);
    int update(Student s);
    int delete(Long id);
    List<StudentCourseDTO> getStudentCourseDetails();
    List<String> getAllNames();
}