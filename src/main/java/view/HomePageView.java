package view;

import javax.swing.*;

import dao.ContractDAO;
import dao.RoomDAO;
import dao.StudentDAO;
import java.awt.*;

public class HomePageView extends JPanel {

    private JLabel lblTotalRooms;
    private JLabel lblAvailableRooms;
    private JLabel lblOccupiedRooms;
    private JLabel lblTotalStudents;
    private JLabel lblMaleStudents;
    private JLabel lblFemaleStudents;
    private JLabel lblBrokenRooms;
    private JLabel lblNewLabel;

    public HomePageView(RoomDAO roomDAO, StudentDAO studentDAO, ContractDAO contractDAO) {
        setBackground(SystemColor.inactiveCaption);
        setLayout(null); // Giữ bố cục tự do

        // Tạo các JPanel riêng cho từng JLabel
        JPanel panelTotalRooms = new JPanel();
        panelTotalRooms.setBackground(SystemColor.activeCaption);
        panelTotalRooms.setBounds(80, 174, 150, 50);
        panelTotalRooms.setLayout(null);
        lblTotalRooms = new JLabel("<html>Total Rooms: <br>" + roomDAO.countTotalRooms() + "</html>");
        lblTotalRooms.setBackground(SystemColor.activeCaption);
        lblTotalRooms.setBounds(0, 0, 150, 50);
        lblTotalRooms.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotalRooms.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelTotalRooms.add(lblTotalRooms);
        
        JPanel panelAvailableRooms = new JPanel();
        panelAvailableRooms.setBackground(SystemColor.activeCaption);
        panelAvailableRooms.setLayout(new BorderLayout());
        panelAvailableRooms.setBounds(240, 174, 150, 50);
        lblAvailableRooms = new JLabel("<html>Available Rooms: <br>" + roomDAO.countAvailableRooms() + "</html>");
        lblAvailableRooms.setHorizontalAlignment(SwingConstants.LEFT);
        lblAvailableRooms.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelAvailableRooms.add(lblAvailableRooms);

        JPanel panelOccupiedRooms = new JPanel();
        panelOccupiedRooms.setBackground(SystemColor.activeCaption);
        panelOccupiedRooms.setLayout(new BorderLayout());
        panelOccupiedRooms.setBounds(400, 174, 150, 50);
        lblOccupiedRooms = new JLabel("<html>Occupied Rooms: <br>" + roomDAO.countOccupiedRooms() + "</html>");
        lblOccupiedRooms.setHorizontalAlignment(SwingConstants.LEFT);
        lblOccupiedRooms.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelOccupiedRooms.add(lblOccupiedRooms);

        JPanel panelTotalStudents = new JPanel();
        panelTotalStudents.setBackground(SystemColor.activeCaption);
        panelTotalStudents.setLayout(new BorderLayout());
        panelTotalStudents.setBounds(80, 235, 150, 50);
        lblTotalStudents = new JLabel("<html>Total Students: <br>" + studentDAO.countTotalStudents() + "</html>");
        lblTotalStudents.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotalStudents.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelTotalStudents.add(lblTotalStudents);

        JPanel panelMaleStudents = new JPanel();
        panelMaleStudents.setBackground(SystemColor.activeCaption);
        panelMaleStudents.setLayout(new BorderLayout());
        panelMaleStudents.setBounds(240, 235, 150, 50);
        lblMaleStudents = new JLabel("<html>Male Students: <br>" + studentDAO.countMaleStudents() + "</html>");
        lblMaleStudents.setHorizontalAlignment(SwingConstants.LEFT);
        lblMaleStudents.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelMaleStudents.add(lblMaleStudents);

        JPanel panelFemaleStudents = new JPanel();
        panelFemaleStudents.setBackground(SystemColor.activeCaption);
        panelFemaleStudents.setLayout(new BorderLayout());
        panelFemaleStudents.setBounds(400, 235, 150, 50);
        lblFemaleStudents = new JLabel("<html>Female Students: <br>" + studentDAO.countFemaleStudents() + "</html>");
        lblFemaleStudents.setHorizontalAlignment(SwingConstants.LEFT);
        lblFemaleStudents.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelFemaleStudents.add(lblFemaleStudents);

        JPanel panelBrokenRooms = new JPanel();
        panelBrokenRooms.setBackground(SystemColor.activeCaption);
        panelBrokenRooms.setLayout(new BorderLayout());
        panelBrokenRooms.setBounds(559, 174, 168, 50);
        lblBrokenRooms = new JLabel("<html>Maintenance Rooms: <br>" + roomDAO.countBrokenRooms() + "</html>");
        lblBrokenRooms.setHorizontalAlignment(SwingConstants.LEFT);
        lblBrokenRooms.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelBrokenRooms.add(lblBrokenRooms);
        
        JPanel panelTotalContract = new JPanel();
        panelTotalContract.setBackground(SystemColor.activeCaption);
        panelTotalContract.setLayout(new BorderLayout());
        panelTotalContract.setBounds(559, 235, 168, 50);
        lblBrokenRooms = new JLabel("<html>Total contracts: <br>" + contractDAO.countTotalContracts() + "</html>");
        lblBrokenRooms.setHorizontalAlignment(SwingConstants.LEFT);
        lblBrokenRooms.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelTotalContract.add(lblBrokenRooms);

        // Thêm các JPanel vào HomePageView
        add(panelTotalRooms);
        add(panelAvailableRooms);
        add(panelOccupiedRooms);
        add(panelTotalStudents);
        add(panelMaleStudents);
        add(panelFemaleStudents);
        add(panelBrokenRooms);
        add(panelTotalContract);

        // Thêm hình nền
        lblNewLabel = new JLabel();
        lblNewLabel.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\bgr for homepage.png"));
        lblNewLabel.setBounds(10, 11, 770, 518);
        add(lblNewLabel);
    }
}
