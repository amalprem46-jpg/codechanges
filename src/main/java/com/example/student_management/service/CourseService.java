package com.example.student_management.service;

import com.example.student_management.model.Course;
import java.util.List;

public interface CourseService {
    int save(Course c);
    List<Course> findAll();
    Course findById(Long id);
    int update(Course c);
    int delete(Long id);
}