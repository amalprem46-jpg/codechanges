package com.example.student_management.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import com.example.student_management.model.Student;
import com.example.student_management.model.StudentCourseDTO;
import com.example.student_management.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;
    public StudentController(StudentService service){this.service=service;}

    @PostMapping
    public int createStudent(@Valid @RequestBody Student student){
        return service.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents(){return service.findAll();}

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id){return service.findById(id);}

    @PutMapping("/{id}")
    public int updateStudent(@PathVariable Long id,@Valid @RequestBody Student student){
        student.setId(id);
        return service.update(student);
    }

    @DeleteMapping("/{id}")
    public int deleteStudent(@PathVariable Long id){return service.delete(id);}

    @GetMapping("/join")
    public List<StudentCourseDTO> getStudentCourseDetails(){return service.getStudentCourseDetails();}

    @GetMapping("/union")
    public List<String> getUnion(){return service.getAllNames();}
}