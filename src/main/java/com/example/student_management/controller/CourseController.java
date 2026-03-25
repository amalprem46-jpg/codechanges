package com.example.student_management.controller;


import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.student_management.model.Course;
import com.example.student_management.service.CourseService;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;
    public CourseController(CourseService service){this.service=service;}

    @PostMapping
    public int createCourse(@Valid @RequestBody Course course){return service.save(course);}
    @GetMapping
    public List<Course> getAllCourses(){return service.findAll();}
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id){return service.findById(id);}
    @PutMapping("/{id}")
    public int updateCourse(@PathVariable Long id,@Valid @RequestBody Course course){
        course.setId(id);
        return service.update(course);
    }
    @DeleteMapping("/{id}")
    public int deleteCourse(@PathVariable Long id){return service.delete(id);}
}