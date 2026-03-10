package com.example.student.service;

import org.springframework.stereotype.Service;
import com.example.student.model.User;
import com.example.student.repository.UserRepository;
import com.example.student.exception.UserNotFoundException;

@Service
public class LoginService {

    private final UserRepository repo;

    public LoginService(UserRepository repo) { this.repo = repo; }

    public String login(String username, String password) {
        User user = repo.findByUsernameAndPassword(username, password);
        if(user == null) throw new UserNotFoundException("Unknown User");
        return "Login Successful";
    }
}