package com.example.student_management.repository;


import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.student_management.model.Course;
import java.util.List;

@Repository
public class CourseRepository {
    private final JdbcTemplate jdbc;
    public CourseRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}

    public int save(Course c){return jdbc.update("INSERT INTO course(course_name) VALUES(?)",c.getCourseName());}
    public List<Course> findAll(){return jdbc.query("SELECT * FROM course",(rs,row)-> new Course(rs.getLong("id"), rs.getString("course_name")));}
    public Course findById(Long id){
        List<Course> list=jdbc.query("SELECT * FROM course WHERE id=?",(rs,row)-> new Course(rs.getLong("id"),rs.getString("course_name")),id);
        return list.isEmpty()?null:list.get(0);
    }
    public int update(Course c){return jdbc.update("UPDATE course SET course_name=? WHERE id=?",c.getCourseName(),c.getId());}
    public int delete(Long id){return jdbc.update("DELETE FROM course WHERE id=?",id);}
}