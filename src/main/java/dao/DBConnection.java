package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dormitorymanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    // Phương thức tạo kết nối
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Đảm bảo nạp driver
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } catch (ClassNotFoundException e) {
                System.err.println("Lỗi: Driver không tìm thấy!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
