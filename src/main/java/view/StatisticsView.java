package view;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import dao.DBConnection;
import dao.RoomDAO;
import dao.StatisDAO;
import dao.StudentDAO;
import java.awt.SystemColor;

public class StatisticsView extends JPanel {

    private JTabbedPane tabbedPane;
    private RoomDAO roomDAO;
    private StudentDAO studentDAO;

    public StatisticsView() throws SQLException {
        // Kết nối tới cơ sở dữ liệu
        Connection connection = DBConnection.getConnection();
        roomDAO = new RoomDAO(connection);
        studentDAO = new StudentDAO(connection);

        // Thiết lập giao diện cơ bản
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(SystemColor.inactiveCaption);

        // Thêm các biểu đồ
        addRoomStatusChart();
        addGenderDistributionChart();
        addCapacityChart();

        // Thêm JTabbedPane vào StatisticsView
        add(tabbedPane); // Thêm JTabbedPane vào JPanel
    }

    private void addRoomStatusChart() throws SQLException {
        // Lấy dữ liệu thống kê
		int available = roomDAO.countAvailableRooms();
		int occupied = roomDAO.countOccupiedRooms();

		// Tạo biểu đồ
		JFreeChart roomStatusChart = StatisDAO.createRoomStatusChart(available, occupied);
		ChartPanel chartPanel = new ChartPanel(roomStatusChart);
		chartPanel.setBackground(SystemColor.inactiveCaption);

		// Thêm biểu đồ vào tab
		tabbedPane.addTab("Room status", chartPanel);
    }

    private void addGenderDistributionChart() throws SQLException {
        // Lấy dữ liệu thống kê
		int male = studentDAO.countMaleStudents();
		int female = studentDAO.countFemaleStudents();

		// Tạo biểu đồ
		JFreeChart genderChart = StatisDAO.createGenderDistributionChart(male, female);
		ChartPanel chartPanel = new ChartPanel(genderChart);
		chartPanel.setBackground(SystemColor.inactiveCaption);

		// Thêm biểu đồ vào tab
		tabbedPane.addTab("Student gender", chartPanel);
    }

    private void addCapacityChart() throws SQLException {
        // Lấy dữ liệu thống kê
		int totalCapacity = roomDAO.countTotalCapacity();
		int totalOccupancy = roomDAO.getTotalOccupancy();

		// Tạo biểu đồ
		JFreeChart capacityChart = StatisDAO.createCapacityChart(totalCapacity, totalOccupancy);
		ChartPanel chartPanel = new ChartPanel(capacityChart);
		chartPanel.setBackground(SystemColor.inactiveCaption);

		// Thêm biểu đồ vào tab
		tabbedPane.addTab("Room capacity", chartPanel);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
