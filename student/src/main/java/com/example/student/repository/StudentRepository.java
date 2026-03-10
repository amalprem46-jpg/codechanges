package com.example.student.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.student.model.Student;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Student> studentMapper = (rs, rowNum) -> {
        Student s = new Student();
        s.setId(rs.getLong("id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setAge(rs.getInt("age"));
        s.setCourse(rs.getString("course"));
        return s;
    };

    public int save(Student student) {
        return jdbc.update("INSERT INTO student(name,email,age,course) VALUES (?,?,?,?)",
                student.getName(), student.getEmail(), student.getAge(), student.getCourse());
    }

    public List<Student> findAll() {
        return jdbc.query("SELECT * FROM student", studentMapper);
    }

    public Student findById(Long id) {
        List<Student> list = jdbc.query("SELECT * FROM student WHERE id=?", studentMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public int update(Student student) {
        return jdbc.update("UPDATE student SET name=?, email=?, age=?, course=? WHERE id=?",
                student.getName(), student.getEmail(), student.getAge(), student.getCourse(), student.getId());
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM student WHERE id=?", id);
    }
}