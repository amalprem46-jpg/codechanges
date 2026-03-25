package com.example.student_management.repository;


import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.student_management.model.User;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;
    public UserRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}

    public User findByUsername(String username){
        List<User> list=jdbc.query("SELECT * FROM app_user WHERE username=?",(rs,row)->
                new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")), username);
        return list.isEmpty()?null:list.get(0);
    }
}