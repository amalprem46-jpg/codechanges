import java.util.*;
import java.sql.*;


interface Student {
    void getStudentName(Scanner sc);
    void getMarks(Scanner sc);
    int getTotal();
    double calculateAvg();
    boolean isPass();
}


interface StudentDBOperations {
    void addStudent(Student s);
    void viewAllStudents();
    void searchStudent(String input);
    void updateStudent(int id, Student s);
    void deleteStudent(int id);
}


class MarkSheet implements Student {
    String name;
    int id;
    int m1, m2, m3;

    public void getStudentName(Scanner sc) {
        while (true) {
            try {
                System.out.print("Enter Student Name: ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) throw new Exception("Name cannot be empty!");
                if (!name.matches("[a-zA-Z ]+")) throw new Exception("Name must contain only letters!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void getMarks(Scanner sc) {
        while (true) {
            try {
                System.out.print("Enter subject 1 mark: ");
                m1 = Integer.parseInt(sc.nextLine());
                System.out.print("Enter subject 2 mark: ");
                m2 = Integer.parseInt(sc.nextLine());
                System.out.print("Enter subject 3 mark: ");
                m3 = Integer.parseInt(sc.nextLine());

                if (m1 < 0 || m1 > 100 || m2 < 0 || m2 > 100 || m3 < 0 || m3 > 100)
                    throw new Exception("Marks must be between 0 and 100!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public int getTotal() { return m1 + m2 + m3; }

    public double calculateAvg() { return getTotal() / 3.0; }

    public boolean isPass() { return m1 >= 35 && m2 >= 35 && m3 >= 35; }
}


class StudentDB implements StudentDBOperations {

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb",
                "studentuser",
                "Student@123"
        );
    }

    public void addStudent(Student s) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO students (name, m1, m2, m3) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            MarkSheet ms = (MarkSheet) s;

            ps.setString(1, ms.name);
            ps.setInt(2, ms.m1);
            ps.setInt(3, ms.m2);
            ps.setInt(4, ms.m3);

            ps.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        try (Connection con = getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                System.out.println("----------------------");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("M1: " + rs.getInt("m1"));
                System.out.println("M2: " + rs.getInt("m2"));
                System.out.println("M3: " + rs.getInt("m3"));
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void searchStudent(String input) {
        try (Connection con = getConnection()) {
            String sql;
            PreparedStatement ps;

            if (input.matches("\\d+")) {
                sql = "SELECT * FROM students WHERE id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(input));
            } else {
                sql = "SELECT * FROM students WHERE name LIKE ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + input + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Found: " + rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void updateStudent(int id, Student s) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE students SET name=?, m1=?, m2=?, m3=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            MarkSheet ms = (MarkSheet) s;

            ps.setString(1, ms.name);
            ps.setInt(2, ms.m1);
            ps.setInt(3, ms.m2);
            ps.setInt(4, ms.m3);
            ps.setInt(5, id);

            ps.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void deleteStudent(int id) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Student deleted successfully!");
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}


public class StudentMarkSheetDB {

    static Scanner sc = new Scanner(System.in);
    static StudentDBOperations db = new StudentDB(); // Interface reference

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("-------MENU-----------");

        while (true) {
            System.out.println("\n1.Add \n2.View \n3.Search \n4.Update \n5.Delete \n6.Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    Student s = new MarkSheet();
                    s.getStudentName(sc);
                    s.getMarks(sc);
                    db.addStudent(s);
                    break;

                case "2":
                    db.viewAllStudents();
                    break;

                case "3":
                    System.out.print("Enter ID or Name: ");
                    db.searchStudent(sc.nextLine());
                    break;

                case "4":
                    System.out.print("Enter ID to update: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Student s2 = new MarkSheet();
                    s2.getStudentName(sc);
                    s2.getMarks(sc);
                    db.updateStudent(id, s2);
                    break;

                case "5":
                    System.out.print("Enter ID to delete: ");
                    db.deleteStudent(Integer.parseInt(sc.nextLine()));
                    break;

                case "6":
                    System.exit(0);
            }
        }
    }
}

