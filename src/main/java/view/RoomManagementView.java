package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Room;
import model.Student;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomManagementView extends JPanel {
    private JTextField txtRoomNumber;
    private JTextField txtCapacity;
    private JTextField txtOccupied;
    private JComboBox<String> comboStatus;
    private JTable roomTable;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
	private DefaultTableModel roomModel;
	private JButton searchButton;
	private JLabel roomTypeLabel;
	private JComboBox<String> roomTypeComboBox;
	private JButton btnRefresh;
	private JLabel lblNewLabel;
	private JButton btnExportPDF;

	public RoomManagementView() {
	    setBackground(SystemColor.inactiveCaption);

	    // Panel nhập liệu
	    JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
	    inputPanel.setBackground(SystemColor.inactiveCaption);
	    inputPanel.setBounds(10, 14, 568, 222);
	    inputPanel.setBorder(BorderFactory.createTitledBorder("Room information"));

	    inputPanel.add(new JLabel("Room number:"));
	    txtRoomNumber = new JTextField();
	    inputPanel.add(txtRoomNumber);

	    inputPanel.add(new JLabel("Capacity:"));
	    txtCapacity = new JTextField();
	    inputPanel.add(txtCapacity);

	    inputPanel.add(new JLabel("Occupancy:"));
	    txtOccupied = new JTextField();
	    inputPanel.add(txtOccupied);

	    inputPanel.add(new JLabel("Status:"));
	    comboStatus = new JComboBox<>(new String[]{"", "Available", "Occupied", "Maintenance"});
	    inputPanel.add(comboStatus);

	    inputPanel.add(new JLabel("Room type:"));
	    roomTypeComboBox = new JComboBox<>(new String[]{"", "Male", "Female"});
	    inputPanel.add(roomTypeComboBox);

	    // Bảng hiển thị thông tin
	    String[] columnNames = {"Room number", "Capacity", "Occupancy", "Status", "Room type"};
	    Object[][] data = {};
	    roomModel = new DefaultTableModel(columnNames, 0);
	    roomTable = new JTable(roomModel);
	    roomTable.setBackground(SystemColor.activeCaption);
	    roomTable.setFillsViewportHeight(true);
	    JScrollPane scrollPane = new JScrollPane(roomTable);
	    scrollPane.setBounds(10, 247, 770, 282);

	    // Tạo các JPanel riêng cho từng JButton với layout null
	    JPanel panelAdd = new JPanel();
	    panelAdd.setLayout(null); // Sử dụng null layout để setBounds cho từng nút
	    panelAdd.setBounds(630, 10, 113, 23);
	    btnAdd = new JButton("Add");
	    btnAdd.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\add icon.png"));
	    btnAdd.setBackground(SystemColor.activeCaption);
	    btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnAdd.setBounds(0, 0, 113, 23); // Thiết lập kích thước và vị trí cho JButton
	    panelAdd.add(btnAdd);

	    JPanel panelEdit = new JPanel();
	    panelEdit.setLayout(null);
	    panelEdit.setBounds(630, 50, 113, 23); // Đặt panelEdit xuống dưới panelAdd
	    btnEdit = new JButton("Edit");
	    btnEdit.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\edit.png"));
	    btnEdit.setBackground(SystemColor.activeCaption);
	    btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnEdit.setBounds(0, 0, 113, 23);
	    panelEdit.add(btnEdit);

	    JPanel panelDelete = new JPanel();
	    panelDelete.setLayout(null);
	    panelDelete.setBounds(630, 90, 113, 23); // Đặt panelDelete xuống dưới panelEdit
	    btnDelete = new JButton("Delete");
	    btnDelete.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\Delete.png"));
	    btnDelete.setBackground(SystemColor.activeCaption);
	    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnDelete.setBounds(0, 0, 113, 23);
	    panelDelete.add(btnDelete);

	    JPanel panelSearch = new JPanel();
	    panelSearch.setLayout(null);
	    panelSearch.setBounds(630, 130, 113, 23); // Đặt panelSearch xuống dưới panelDelete
	    searchButton = new JButton("Search");
	    searchButton.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\Search.png"));
	    searchButton.setBackground(SystemColor.activeCaption);
	    searchButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    searchButton.setBounds(0, 0, 113, 23);
	    panelSearch.add(searchButton);

	    JPanel panelRefresh = new JPanel();
	    panelRefresh.setLayout(null);
	    panelRefresh.setBounds(630, 170, 113, 23); // Đặt panelRefresh xuống dưới panelSearch
	    btnRefresh = new JButton("Refresh");
	    btnRefresh.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\refresh.png"));
	    btnRefresh.setBackground(SystemColor.activeCaption);
	    btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnRefresh.setBounds(0, 0, 113, 23);
	    panelRefresh.add(btnRefresh);
	    
	    JPanel panelExport = new JPanel();
	    panelExport.setLayout(null);
	    panelExport.setBounds(630, 210, 113, 23); // Đặt ngay dưới panelRefresh
	    btnExportPDF = new JButton("Export PDF");
	    btnExportPDF.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\pdf-icon.png")); // sửa đường dẫn icon nếu cần
	    btnExportPDF.setBackground(SystemColor.activeCaption);
	    btnExportPDF.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    btnExportPDF.setBounds(0, 0, 113, 23);
	    panelExport.add(btnExportPDF);
	    
	    
	    add(panelExport);
	    add(panelAdd);
	    add(panelEdit);
	    add(panelDelete);
	    add(panelSearch);
	    add(panelRefresh);

	    setLayout(null);

	    // Thêm các phần vào view
	    add(inputPanel);
	    add(scrollPane);


	    // Thêm hình nền
	    lblNewLabel = new JLabel("New label");
	    lblNewLabel.setIcon(new ImageIcon("src\\\\main\\\\java\\\\dormitory apps\\information about dormitories.png"));
	    lblNewLabel.setBounds(0, 0, 790, 540);
	    add(lblNewLabel);
	}


    
	// Các phương thức getter để controller thao tác
    public String getRoomNumber() {
        return txtRoomNumber.getText().trim();
    }

    public int getCapacity() {
        try {
            return Integer.parseInt(txtCapacity.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // Xử lý khi nhập sai định dạng
        }
    }

    public int getOccupied() {
        try {
            return Integer.parseInt(txtOccupied.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // Xử lý khi nhập sai định dạng
        }
    }
    
    public String getCapacityTxt() {
		return txtCapacity.getText().trim();
    }
    
    public String getOccupiedTxt() {
    	return txtOccupied.getText().trim();
    }

    public String getStatus() {
        return (String) comboStatus.getSelectedItem();
    }

    public JTable getRoomTable() {
        return roomTable;
    }

    public JButton getAddButton() {
        return btnAdd;
    }

    public JButton getEditButton() {
        return btnEdit;
    }

    public JButton getDeleteButton() {
        return btnDelete;
    }
    
    public JButton getSearchButton(){
    	return searchButton;
    }
    
    public String getRoomTypeInput() {
        return (String) roomTypeComboBox.getSelectedItem();
    }
    
    public JButton getBtnRefresh() {
		return btnRefresh;
	}
    
    



    // Các phương thức đặt lại giá trị cho các ô nhập liệu
    public void resetInputFields() {
        txtRoomNumber.setText("");
        txtCapacity.setText("");
        txtOccupied.setText("");
        comboStatus.setSelectedIndex(0);
    }

	public void updateRoomTable(List<Room> room) {
		roomModel.setRowCount(0); // xóa dữ liệu cũ
		for (Room rooms : room) {
            Object[] rowData = {
                rooms.getRoomNumber(),
                rooms.getCapacity(),
                rooms.getOccupied(),
                rooms.getStatus(),
                rooms.getRoomType()
            };
            roomModel.addRow(rowData);
        }
		
	}
	
	// Lấy số phòng được chọn trong bảng
	public String getSelectedRoomNumber() {
	    int selectedRow = roomTable.getSelectedRow();
	    if (selectedRow != -1) {
	        return String.valueOf(roomModel.getValueAt(selectedRow, 0)); // Số phòng nằm ở cột đầu tiên
	    }
	    return null; // Trả về null nếu không có hàng nào được chọn
	}



	public JButton getBtnExportPDF() {
		return btnExportPDF;
	}

	public void setBtnExportPDF(JButton btnExportPDF) {
		this.btnExportPDF = btnExportPDF;
	}
	
	
}
