package com.example.student.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.exception.UserNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) { this.repo = repo; }

    public int saveStudent(Student student) { return repo.save(student); }

    public List<Student> getAllStudents() { return repo.findAll(); }

    public Student getStudentById(Long id) {
        Student s = repo.findById(id);
        if(s == null) throw new UserNotFoundException("Student not found");
        return s;
    }

    public int updateStudent(Student student) {
        return repo.update(student);
    }

    public int deleteStudent(Long id) {
        return repo.delete(id);
    }
}