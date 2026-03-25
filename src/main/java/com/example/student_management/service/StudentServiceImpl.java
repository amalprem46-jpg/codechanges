package com.example.student_management.service;

import org.springframework.stereotype.Service;
import com.example.student_management.model.Student;
import com.example.student_management.model.StudentCourseDTO;
import com.example.student_management.repository.StudentRepository;
import com.example.student_management.exception.CustomException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;
    public StudentServiceImpl(StudentRepository repo){this.repo=repo;}

    @Override
    public int save(Student s){
        if(s.getName()==null || s.getName().isEmpty())
            throw new CustomException("VAL101","Student name cannot be empty");
        return repo.save(s);
    }

    @Override
    public List<Student> findAll(){return repo.findAll();}
    @Override
    public Student findById(Long id){
        Student s=repo.findById(id);
        if(s==null) throw new CustomException("STU404","Student not found");
        return s;
    }
    @Override
    public int update(Student s){return repo.update(s);}
    @Override
    public int delete(Long id){return repo.delete(id);}
    @Override
    public List<StudentCourseDTO> getStudentCourseDetails(){return repo.getStudentCourseDetails();}
    @Override
    public List<String> getAllNames(){return repo.getAllNames();}
}