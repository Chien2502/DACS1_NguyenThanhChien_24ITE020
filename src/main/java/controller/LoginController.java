package controller;

import dao.UserDAO;
import dao.DBConnection;
import view.Dashboard;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {
	private LoginView loginView;
	private UserDAO userDAO;

	public LoginController(LoginView loginView) {
		this.loginView = loginView;

		try {
			// Kết nối cơ sở dữ liệu và khởi tạo UserDAO
			Connection connection = DBConnection.getConnection();
			this.userDAO = new UserDAO(connection);

			// Xử lý sự kiện nút đăng nhập
			this.loginView.getLoginButton().addActionListener(e -> {
				try {
					authenticate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "\"Unable to connect to the database!", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void authenticate() throws SQLException {
        String username = loginView.getUsernameField();
        String password = new String(loginView.getPasswordField());
        try{
        	if(userDAO.authenticate(username, password)) {
        		JOptionPane.showMessageDialog(loginView, "Login successful!");
            loginView.dispose(); // Đóng cửa sổ đăng nhập
            new Dashboard(); // Mở giao diện chính
        	}
        	else {
        		JOptionPane.showMessageDialog(loginView,
                	"Incorrect username or password!", "Login Fail", JOptionPane.ERROR_MESSAGE);
        	}
        }catch(IllegalArgumentException e1) {
        	JOptionPane.showMessageDialog(loginView,
            		"Incorrect username or password!", "Login Fail", JOptionPane.ERROR_MESSAGE);
        }
    }
}
