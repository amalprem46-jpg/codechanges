package com.example.student_management.controller;


import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.student_management.model.Enrollment;
import com.example.student_management.service.EnrollmentService;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;
    public EnrollmentController(EnrollmentService service){this.service=service;}

    @PostMapping
    public int createEnrollment(@Valid @RequestBody Enrollment e){return service.save(e);}
    @GetMapping
    public List<Enrollment> getAllEnrollments(){return service.findAll();}
    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(@PathVariable Long id){return service.findById(id);}
    @PutMapping("/{id}/mark")
    public int updateMark(@PathVariable Long id,@RequestBody Enrollment e){return service.updateMark(id,e.getMark());}
    @DeleteMapping("/{id}")
    public int deleteEnrollment(@PathVariable Long id){return service.delete(id);}
}
