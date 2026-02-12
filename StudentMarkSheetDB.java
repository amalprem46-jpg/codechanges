import java.util.Scanner;
import java.sql.*;


class Student {
    String name;
    int id;

    void getStudentName(Scanner sc) {
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
}


class MarkSheet extends Student {
    int m1, m2, m3;

    void getMarks(Scanner sc) {
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

    int getTotal() { return m1 + m2 + m3; }

    double calculateAvg() { return getTotal() / 3.0; }

    boolean isPass() { return m1 >= 35 && m2 >= 35 && m3 >= 35; }
}


public class StudentMarkSheetDB {

    static Scanner sc = new Scanner(System.in);

   
    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studentdb",
            "studentuser",
            "Student@123"
        );
    }

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            return;
        }

        while (true) {
            showMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudents(); break;
                case "2": viewAllStudents(); break;
                case "3": searchStudentByIdOrName(); break;
                case "4": updateStudent(); break;
                case "5": deleteStudent(); break;
                case "6": viewStudentsSortedByTotal(); break;
                case "7": viewStatistics(); break;
                case "8": 
                    System.out.println("Exiting program. Goodbye!"); 
                    sc.close(); 
                    return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void showMenu() {
        System.out.println("\n==== STUDENT MARKSHEET MENU ====");
        System.out.println("1. Add new students");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID or Name");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("6. View students sorted by total marks");
        System.out.println("7. View class statistics");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    // 🔹 Add Student
    static void addStudents() {
        int n;
        try {
            System.out.print("Enter number of students to add: ");
            n = Integer.parseInt(sc.nextLine());
        } catch (Exception e) { System.out.println("Invalid number."); return; }

        try (Connection con = getConnection()) {
            String sql = "INSERT INTO students (name, m1, m2, m3) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter Details of Student " + (i + 1));
                MarkSheet student = new MarkSheet();
                student.getStudentName(sc);
                student.getMarks(sc);

                ps.setString(1, student.name);
                ps.setInt(2, student.m1);
                ps.setInt(3, student.m2);
                ps.setInt(4, student.m3);

                ps.executeUpdate();
                System.out.println("Student added successfully!");
            }
        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 View All Students
    static void viewAllStudents() {
        try (Connection con = getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            System.out.println("\n==== ALL STUDENTS ====");
            while (rs.next()) {
                MarkSheet s = new MarkSheet();
                s.id = rs.getInt("id");
                s.name = rs.getString("name");
                s.m1 = rs.getInt("m1");
                s.m2 = rs.getInt("m2");
                s.m3 = rs.getInt("m3");
                displayStudent(s);
            }
        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 Search by ID or Name (partial allowed)
    static void searchStudentByIdOrName() {
        System.out.print("Enter ID or Name to search: ");
        String input = sc.nextLine().trim();
        try (Connection con = getConnection()) {
            String sql;
            PreparedStatement ps;

            if (input.matches("\\d+")) { // If input is all digits -> search by ID
                sql = "SELECT * FROM students WHERE id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(input));
            } else { // Search by name (partial)
                sql = "SELECT * FROM students WHERE name LIKE ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + input + "%");
            }

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                MarkSheet s = new MarkSheet();
                s.id = rs.getInt("id");
                s.name = rs.getString("name");
                s.m1 = rs.getInt("m1");
                s.m2 = rs.getInt("m2");
                s.m3 = rs.getInt("m3");
                displayStudent(s);
            }
            if (!found) System.out.println("No student found.");
        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 Update Student
    static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        int updateId;
        try { updateId = Integer.parseInt(sc.nextLine()); } 
        catch (Exception e) { System.out.println("Invalid ID format."); return; }

        try (Connection con = getConnection()) {
            String checkSql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, updateId);
            ResultSet rs = checkPs.executeQuery();
            if (!rs.next()) { System.out.println("Student not found."); return; }

            System.out.println("Enter new details:");
            MarkSheet student = new MarkSheet();
            student.getStudentName(sc);
            student.getMarks(sc);

            String updateSql = "UPDATE students SET name=?, m1=?, m2=?, m3=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setString(1, student.name);
            ps.setInt(2, student.m1);
            ps.setInt(3, student.m2);
            ps.setInt(4, student.m3);
            ps.setInt(5, updateId);

            if (ps.executeUpdate() > 0) System.out.println("Student updated successfully!");
            else System.out.println("Update failed.");

        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

   
    static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int deleteId;
        try { deleteId = Integer.parseInt(sc.nextLine()); } 
        catch (Exception e) { System.out.println("Invalid ID format."); return; }

        try (Connection con = getConnection()) {
            String checkSql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, deleteId);
            ResultSet rs = checkPs.executeQuery();
            if (!rs.next()) { System.out.println("Student not found."); return; }

            String studentName = rs.getString("name");
            System.out.print("Are you sure you want to delete student \"" + studentName + "\"? (Y/N): ");
            String confirm = sc.nextLine().trim();
            if (!confirm.equalsIgnoreCase("Y")) { System.out.println("Deletion cancelled."); return; }

            String deleteSql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(deleteSql);
            ps.setInt(1, deleteId);
            if (ps.executeUpdate() > 0) System.out.println("Student deleted successfully!");
            else System.out.println("Delete failed.");

        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 View students sorted by total marks
    static void viewStudentsSortedByTotal() {
        try (Connection con = getConnection()) {
            String sql = "SELECT *, (m1+m2+m3) AS total FROM students ORDER BY total DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n==== STUDENTS SORTED BY TOTAL MARKS ====");
            while (rs.next()) {
                MarkSheet s = new MarkSheet();
                s.id = rs.getInt("id");
                s.name = rs.getString("name");
                s.m1 = rs.getInt("m1");
                s.m2 = rs.getInt("m2");
                s.m3 = rs.getInt("m3");
                displayStudent(s);
            }
        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 Class Statistics
    static void viewStatistics() {
        try (Connection con = getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS totalStudents, " +
                    "AVG(m1) AS avgM1, AVG(m2) AS avgM2, AVG(m3) AS avgM3, " +
                    "MAX(m1) AS maxM1, MAX(m2) AS maxM2, MAX(m3) AS maxM3, " +
                    "MIN(m1) AS minM1, MIN(m2) AS minM2, MIN(m3) AS minM3 " +
                    "FROM students");
            if (rs.next()) {
                System.out.println("\n==== CLASS STATISTICS ====");
                System.out.println("Total Students: " + rs.getInt("totalStudents"));
                System.out.printf("Average Marks: Subject1: %.2f, Subject2: %.2f, Subject3: %.2f\n",
                        rs.getDouble("avgM1"), rs.getDouble("avgM2"), rs.getDouble("avgM3"));
                System.out.println("Highest Marks: Subject1: " + rs.getInt("maxM1") +
                        ", Subject2: " + rs.getInt("maxM2") + ", Subject3: " + rs.getInt("maxM3"));
                System.out.println("Lowest Marks: Subject1: " + rs.getInt("minM1") +
                        ", Subject2: " + rs.getInt("minM2") + ", Subject3: " + rs.getInt("minM3"));
            } else {
                System.out.println("No data available.");
            }
        } catch (Exception e) { System.out.println("Database Error: " + e.getMessage()); }
    }

    // 🔹 Display Student
    static void displayStudent(MarkSheet s) {
        System.out.println("\nStudent ID: " + s.id);
        System.out.println("Student Name: " + s.name);
        System.out.println("Subject 1 mark: " + s.m1);
        System.out.println("Subject 2 mark: " + s.m2);
        System.out.println("Subject 3 mark: " + s.m3);
        System.out.println("Total: " + s.getTotal());
        System.out.printf("Average: %.2f\n", s.calculateAvg());
        System.out.println("Status: " + (s.isPass() ? "Pass" : "Fail"));
        System.out.println("--------------------------");
    }
}

