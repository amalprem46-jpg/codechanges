package com.example.student_management.service;


import org.springframework.stereotype.Service;
import com.example.student_management.repository.EnrollmentRepository;
import com.example.student_management.model.Enrollment;
import com.example.student_management.exception.CustomException;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository repo;
    public EnrollmentServiceImpl(EnrollmentRepository repo){this.repo=repo;}

    @Override
    public int save(Enrollment e){
        if(e.getMark()<0 || e.getMark()>100)
            throw new CustomException("VAL301","Mark must be 0-100");
        return repo.save(e);
    }

    @Override
    public List<Enrollment> findAll(){return repo.findAll();}

    @Override
    public Enrollment findById(Long id){
        Enrollment e=repo.findById(id);
        if(e==null) throw new CustomException("ENR404","Enrollment not found");
        return e;
    }

    @Override
    public int updateMark(Long id,int mark){
        if(mark<0 || mark>100) throw new CustomException("VAL301","Mark must be 0-100");
        return repo.updateMark(id,mark);
    }

    @Override
    public int delete(Long id){return repo.delete(id);}
}