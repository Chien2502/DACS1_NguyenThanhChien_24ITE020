package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean authenticate(String username, String password) {
        // 1. Lấy hash đã lưu trong DB cho username
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    // 2. Kiểm tra password raw vs. stored hash
                    if (BCrypt.checkpw(password, storedHash)) {
                        return true;    
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  
    }
}
