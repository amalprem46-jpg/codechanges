package com.example.student.service;

import java.util.List;
import com.example.student.model.Student;

public interface StudentService {

    int saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    int updateStudent(Student student);

    int deleteStudent(Long id);
}