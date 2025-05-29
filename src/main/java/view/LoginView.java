package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.border.BevelBorder;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    private JCheckBox rememberMeCheckbox;
    public LoginView() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\java\\dormitory apps\\hall.png"));
    	getContentPane().setBackground(new Color(220, 240, 245));
    	setBackground(new Color(220, 240, 245));
        setTitle("Login Dormitory Managerment System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel nhập thông tin
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(null);
        inputPanel.setBackground(new Color(61, 65, 77));
        inputPanel.setBounds(71, 206, 361, 171);
        inputPanel.setLayout(null);
        JLabel lblAccount = new JLabel("Account: ");
        lblAccount.setForeground(new Color(255, 255, 255));
        lblAccount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAccount.setBackground(SystemColor.activeCaption);
        lblAccount.setBounds(0, 15, 106, 40);
        inputPanel.add(lblAccount);
        usernameField = new JTextField();
        usernameField.setBounds(116, 15, 232, 40);
        inputPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setForeground(new Color(255, 255, 255));
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setBounds(0, 70, 106, 40);
        inputPanel.add(lblPassword);
        passwordField = new JPasswordField();
        passwordField.setToolTipText("");
        passwordField.setBounds(116, 70, 232, 40);
        inputPanel.add(passwordField);
        getContentPane().setLayout(null);
        getContentPane().add(inputPanel);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        loginButton.setIcon(new ImageIcon("src\\main\\java\\dormitory apps\\login icon.png"));
        loginButton.setBounds(136, 129, 87, 23);
        inputPanel.add(loginButton);
        loginButton.setBackground(SystemColor.activeCaption);
        exitButton = new JButton("Exit");
        exitButton.setIcon(new ImageIcon("src\\main\\java\\dormitory apps\\logout icon.png"));
        exitButton.setBounds(247, 129, 87, 23);
        inputPanel.add(exitButton);
        exitButton.setBackground(SystemColor.activeCaption);
        
        // Thêm checkbox vào panel
//        rememberMeCheckbox = new JCheckBox("Remember me\r\n");
//        rememberMeCheckbox.setForeground(new Color(255, 255, 255));
//        rememberMeCheckbox.setBounds(12, 129, 118, 23);
//        inputPanel.add(rememberMeCheckbox);
//        rememberMeCheckbox.setBackground(new Color(61, 65, 77));
        
        JLabel label = new JLabel("New label");
        label.setBounds(-24, 96, 49, -73);
        inputPanel.add(label);
        
        // Hành động nút thoát
        exitButton.addActionListener(e -> System.exit(0));
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("src\\main\\java\\dormitory apps\\backgrn login1.png"));
        lblNewLabel.setBounds(0, 0, 786, 463);
        getContentPane().add(lblNewLabel);
        setVisible(true);
    }
    

    public boolean isRememberMeSelected() {
        return rememberMeCheckbox.isSelected();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }
}
