package com.example.student.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    public int createStudent(@Valid @RequestBody Student student) {
        return service.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() { return service.getAllStudents(); }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) { return service.getStudentById(id); }

    @PutMapping("/{id}")
    public int updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return service.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public int deleteStudent(@PathVariable Long id) { return service.deleteStudent(id); }
}