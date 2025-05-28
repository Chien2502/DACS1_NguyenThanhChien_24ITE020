// Dashboard.java
package view;

import javax.swing.*;

import controller.ContractController;
import controller.RoomController;
import controller.StudentController;
import dao.ContractDAO;
import dao.DBConnection;
import dao.RoomDAO;
import dao.StudentDAO;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Connection connection = DBConnection.getConnection();
    private RoomDAO roomDAO = new RoomDAO(connection);
    private StudentDAO studentDAO = new StudentDAO(connection);
    private ContractDAO contractDAO = new ContractDAO(connection);

    public Dashboard() throws SQLException {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\picture\\dormitory apps\\hall.png"));
        setTitle("Dormitory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SystemColor.activeCaption);
        
        JMenuItem menuStatis = new JMenuItem("Statistical");
        menuStatis.setBackground(SystemColor.activeCaption);
        menuStatis.addActionListener(e -> showView("StatisticsView"));
        
        JMenu menuDashboard = new JMenu("Dashboard");
        menuDashboard.setBackground(SystemColor.activeCaption);
        menuBar.add(menuDashboard);
        
        JMenuItem dashboardMenuItem = new JMenuItem("Home page");
        dashboardMenuItem.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\dashboard.png"));
        menuDashboard.add(dashboardMenuItem);
        dashboardMenuItem.addActionListener(e -> showView("Home page"));
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\logout icon.png"));
        menuDashboard.add(exitMenuItem);
        exitMenuItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    null,                                       
                    "Do you want exit?",
                    "Confirm exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        JMenu menuManager = new JMenu("Managerment");
        menuManager.setBackground(SystemColor.activeCaption);
        
        JMenuItem studentMenuItem = new JMenuItem("Student Management");
        studentMenuItem.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Student-3-icon.png"));
        studentMenuItem.addActionListener(e -> showView("StudentManagement"));

        JMenuItem contractMenuItem = new JMenuItem("Contract Management");
        contractMenuItem.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Iconka-Business-Finance-Contract.16.png"));
        contractMenuItem.addActionListener(e -> showView("ContractManagement"));

        menuManager.add(studentMenuItem);
        menuManager.add(contractMenuItem);
        menuBar.add(menuManager);
        
        JMenuItem roomMenuItem = new JMenuItem("Room Management");
        roomMenuItem.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Icons8-Ios7-Household-Room.16.png"));
        roomMenuItem.addActionListener(e -> showView("RoomManagement"));
        menuManager.add(roomMenuItem);
        menuBar.add(menuStatis);
        setJMenuBar(menuBar);

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add views
        HomePageView dashboardview = new HomePageView(roomDAO, studentDAO, contractDAO);
        mainPanel.add(dashboardview, "Home page");
        
        StudentManagementView studentView = new StudentManagementView();
        studentView.getBtnAdd().setIcon(new ImageIcon("D:\\picture\\dormitory apps\\add icon.png"));
        StudentController studentCtrl = new StudentController(studentView);
        mainPanel.add(studentView, "StudentManagement");
        
        RoomManagementView roomView = new RoomManagementView();
        RoomController roomCtrl = new RoomController(roomView);
        mainPanel.add(roomView, "RoomManagement");
        
        ContractManagementView contractManagementView = new ContractManagementView();
        ContractController contractCtrl = new ContractController(contractManagementView);
        mainPanel.add(contractManagementView, "ContractManagement");
        
        StatisticsView statisticsView = new StatisticsView();
        mainPanel.add(statisticsView, "StatisticsView");

        getContentPane().add(mainPanel);

        setVisible(true);
    }

    private void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }
}
