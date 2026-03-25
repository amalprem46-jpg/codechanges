package com.example.student_management.service;

import org.springframework.stereotype.Service;
import com.example.student_management.model.User;
import com.example.student_management.repository.UserRepository;
import com.example.student_management.exception.CustomException;

@Service
public class LoginService {
    private final UserRepository repo;
    public LoginService(UserRepository repo){this.repo=repo;}

    public String login(User u){
        User dbUser=repo.findByUsername(u.getUsername());
        if(dbUser==null) throw new CustomException("USR203","Unknown person login");
        if(!dbUser.getPassword().equals(u.getPassword()))
            throw new CustomException("USR203","Invalid password");
        return "Login successful";
    }
}