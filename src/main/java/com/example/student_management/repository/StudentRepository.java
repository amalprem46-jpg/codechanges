package com.example.student_management.repository;


import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.student_management.model.Student;
import com.example.student_management.model.StudentCourseDTO;
import java.util.List;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbc;
    public StudentRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}

    public int save(Student s){
        return jdbc.update("INSERT INTO student(name,email,age) VALUES(?,?,?)",
                s.getName(),s.getEmail(),s.getAge());
    }

    public List<Student> findAll(){
        return jdbc.query("SELECT * FROM student",(rs,row)-> new Student(
                rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getInt("age")
        ));
    }

    public Student findById(Long id){
        List<Student> list=jdbc.query("SELECT * FROM student WHERE id=?",(rs,row)->
                new Student(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getInt("age")), id);
        return list.isEmpty()?null:list.get(0);
    }

    public int update(Student s){
        return jdbc.update("UPDATE student SET name=?,email=?,age=? WHERE id=?",
                s.getName(),s.getEmail(),s.getAge(),s.getId());
    }

    public int delete(Long id){
        return jdbc.update("DELETE FROM student WHERE id=?",id);
    }

    public List<StudentCourseDTO> getStudentCourseDetails(){
        String sql = "SELECT s.name,c.course_name,e.mark FROM student s INNER JOIN enrollment e ON s.id=e.student_id INNER JOIN course c ON c.id=e.course_id";
        return jdbc.query(sql,(rs,row)-> new StudentCourseDTO(rs.getString("name"), rs.getString("course_name"), rs.getInt("mark")));
    }

    public List<String> getAllNames(){
        String sql="SELECT name FROM student UNION SELECT course_name FROM course";
        return jdbc.query(sql,(rs,row)-> rs.getString(1));
    }
}