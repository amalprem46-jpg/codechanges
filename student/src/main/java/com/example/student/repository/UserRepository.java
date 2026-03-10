package com.example.student.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.student.model.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        return u;
    };

    public User findByUsernameAndPassword(String username, String password) {
    	List<User> list = jdbc.query(
    		    "SELECT * FROM app_user WHERE username=? AND password=?",
    		    userMapper, username, password
    		);
        return list.isEmpty() ? null : list.get(0);
    }

    public int save(User user) {
        return jdbc.update("INSERT INTO app_user(username,password) VALUES (?,?)",
        	    user.getUsername(), user.getPassword());
    }
}