package com.example.student_management.repository;



import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.student_management.model.Enrollment;
import java.util.List;

@Repository
public class EnrollmentRepository {
    private final JdbcTemplate jdbc;
    public EnrollmentRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}

    public int save(Enrollment e){return jdbc.update("INSERT INTO enrollment(student_id,course_id,mark) VALUES(?,?,?)",e.getStudentId(),e.getCourseId(),e.getMark());}
    public List<Enrollment> findAll(){return jdbc.query("SELECT * FROM enrollment",(rs,row)-> new Enrollment(rs.getLong("id"), rs.getLong("student_id"), rs.getLong("course_id"), rs.getInt("mark")));}
    public Enrollment findById(Long id){
        List<Enrollment> list=jdbc.query("SELECT * FROM enrollment WHERE id=?",(rs,row)-> new Enrollment(rs.getLong("id"), rs.getLong("student_id"), rs.getLong("course_id"), rs.getInt("mark")),id);
        return list.isEmpty()?null:list.get(0);
    }
    public int updateMark(Long id,int mark){return jdbc.update("UPDATE enrollment SET mark=? WHERE id=?",mark,id);}
    public int delete(Long id){return jdbc.update("DELETE FROM enrollment WHERE id=?",id);}
}