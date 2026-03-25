package com.example.student_management.service;

import com.example.student_management.model.Enrollment;
import java.util.List;

public interface EnrollmentService {
    int save(Enrollment e);
    List<Enrollment> findAll();
    Enrollment findById(Long id);
    int updateMark(Long id,int mark);
    int delete(Long id);
}
