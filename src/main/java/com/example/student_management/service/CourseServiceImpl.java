package com.example.student_management.service;


import org.springframework.stereotype.Service;
import com.example.student_management.repository.CourseRepository;
import com.example.student_management.model.Course;
import com.example.student_management.exception.CustomException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;
    public CourseServiceImpl(CourseRepository repo){this.repo=repo;}

    @Override
    public int save(Course c){
        if(c.getCourseName()==null || c.getCourseName().isEmpty())
            throw new CustomException("VAL201","Course name cannot be empty");
        return repo.save(c);
    }

    @Override
    public List<Course> findAll(){return repo.findAll();}
    @Override
    public Course findById(Long id){
        Course c=repo.findById(id);
        if(c==null) throw new CustomException("CRS404","Course not found");
        return c;
    }
    @Override
    public int update(Course c){return repo.update(c);}
    @Override
    public int delete(Long id){return repo.delete(id);}
}