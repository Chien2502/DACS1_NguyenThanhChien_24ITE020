package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class StudentDAO {
    private static Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    // Lấy danh sách tất cả sinh viên
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students ORDER BY room_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        	
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("room_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Thêm sinh viên mới
    public boolean addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (id, name, dob, gender, phone, email, room_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setString(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setDate(3, student.getDob());
            stmt.setString(4, student.getGender());
            stmt.setString(5, student.getPhone()); 
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getRoomId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật sinh viên
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, dob = ?, gender = ?, phone = ?, email = ?, room_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setDate(2, student.getDob());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getEmail());
            stmt.setString(6, student.getRoomId());
            stmt.setString(7, student.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa sinh viên
    public boolean deleteStudent(String selectedId) {
        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, selectedId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Student> searchStudents(String id, String fullName, String dob, String gender, String phoneNumber, String email, String roomId) {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");

        // Thêm điều kiện nếu tham số không null hoặc không rỗng
        if (id != null && !id.isEmpty()) {
            sql.append(" AND id LIKE ?");
        }
        if (fullName != null && !fullName.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (dob != null && !dob.isEmpty()) {
            sql.append(" AND dob LIKE ?");
        }
        if (gender != null && !gender.trim().isEmpty()) {
            sql.append(" AND gender = ?");
            System.out.println(gender);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            sql.append(" AND phone LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
        }
        if (roomId != null && !roomId.isEmpty()) {
            sql.append(" AND room_id LIKE ?");
        }
        sql.append(" ORDER BY room_id");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (id != null && !id.isEmpty()) {
                statement.setString(index++, "%" + id + "%");
            }
            if (fullName != null && !fullName.isEmpty()) {
                statement.setString(index++, "%" + fullName + "%");
            }
            if (dob != null && !dob.isEmpty()) {
                statement.setString(index++, "%" + dob + "%");
            }
            if (gender != null && !gender.trim().isEmpty()) {
                statement.setString(index++, gender);
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                statement.setString(index++, "%" + phoneNumber + "%");
            }
            if (email != null && !email.isEmpty()) {
                statement.setString(index++, "%" + email + "%");
            }
            if (roomId != null && !roomId.isEmpty()) {
                statement.setString(index++, "%" + roomId + "%");
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("room_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public static Student getStudentById(String studentId) {
    	String sql = "SELECT id, name, dob, gender, phone, email, room_id " +
    	        "FROM students WHERE id = ?";
    	try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                // 4. Nếu có kết quả, mapping sang Student
                if (rs.next()) {
                    String   studentId1 = rs.getString("id");
                    String   name      = rs.getString("name");
                    Date     dob       = rs.getDate("dob");
                    String   gender    = rs.getString("gender");
                    String   phone     = rs.getString("phone");
                    String   email     = rs.getString("email");
                    String   roomId    = rs.getString("room_id");

                    return new Student(
                        studentId1,
                        name,
                        dob,
                        gender,
                        phone,
                        email,
                        roomId
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
		return null;
    }

    public static int countMaleStudents() {
        String sql = "SELECT COUNT(*) FROM students WHERE gender = 'Male'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countFemaleStudents() {
        String sql = "SELECT COUNT(*) FROM students WHERE gender = 'Female'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String countTotalStudents() {
        String sql = "SELECT COUNT(*) AS total_students FROM students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return String.valueOf(rs.getInt("total_students"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error retrieving total students: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return "0"; // Trả về 0 nếu có lỗi hoặc không có dữ liệu
    }

}
