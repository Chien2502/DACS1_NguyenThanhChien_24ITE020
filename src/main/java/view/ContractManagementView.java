package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Contract;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContractManagementView extends JPanel {
    private JTextField txtRoomNumber;
    private JTextField txtStudentId;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTextField txtMonthlyFee;
    private JTable contractTable;
    private DefaultTableModel contractModel;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
	private JTextField txtId;
	private JButton btnSearch;
	private JButton btnRefreshTable;
	private JButton btnExportPDF;

	public ContractManagementView() {
	    setBackground(SystemColor.inactiveCaption);

	    // Panel nhập liệu
	    JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
	    inputPanel.setBackground(SystemColor.inactiveCaption);
	    inputPanel.setBounds(10, 11, 568, 240);
	    inputPanel.setBorder(BorderFactory.createTitledBorder("Contract information"));

	    inputPanel.add(new JLabel("ID of contract:"));
	    txtId = new JTextField();
	    inputPanel.add(txtId);

	    inputPanel.add(new JLabel("Room number:"));
	    txtRoomNumber = new JTextField();
	    inputPanel.add(txtRoomNumber);

	    inputPanel.add(new JLabel("Student ID:"));
	    txtStudentId = new JTextField();
	    inputPanel.add(txtStudentId);

	    inputPanel.add(new JLabel("Start date (YYYY-MM-DD):"));
	    txtStartDate = new JTextField();
	    inputPanel.add(txtStartDate);

	    inputPanel.add(new JLabel("End date (YYYY-MM-DD):"));
	    txtEndDate = new JTextField();
	    inputPanel.add(txtEndDate);

	    inputPanel.add(new JLabel("Monthly fee:"));
	    txtMonthlyFee = new JTextField();
	    inputPanel.add(txtMonthlyFee);

	    // Bảng hiển thị thông tin hợp đồng
	    String[] columnNames = {"ID of contract", "Room number", "Student ID", "Start date", "End date", "Monthly fee"};
	    contractModel = new DefaultTableModel(columnNames, 0);
	    contractTable = new JTable(contractModel);
	    contractTable.setBackground(SystemColor.activeCaption);
	    contractTable.setFillsViewportHeight(true);
	    JScrollPane scrollPane = new JScrollPane(contractTable);
	    scrollPane.setBounds(10, 262, 770, 267);

	    // Tạo các JPanel riêng cho từng JButton với layout null
	    JPanel panelAdd = new JPanel();
	    panelAdd.setLayout(null); 
	    panelAdd.setBounds(630, 10, 113, 23);
	    btnAdd = new JButton("Add");
	    btnAdd.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\add icon.png"));
	    btnAdd.setBackground(SystemColor.activeCaption);
	    btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnAdd.setBounds(0, 0, 113, 23); 
	    panelAdd.add(btnAdd);

	    JPanel panelEdit = new JPanel();
	    panelEdit.setLayout(null);
	    panelEdit.setBounds(630, 50, 113, 23);
	    btnEdit = new JButton("Edit");
	    btnEdit.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\edit.png"));
	    btnEdit.setBackground(SystemColor.activeCaption);
	    btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnEdit.setBounds(0, 0, 113, 23);
	    panelEdit.add(btnEdit);

	    JPanel panelDelete = new JPanel();
	    panelDelete.setLayout(null);
	    panelDelete.setBounds(630, 90, 113, 23); 
	    btnDelete = new JButton("Delete");
	    btnDelete.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Delete.png"));
	    btnDelete.setBackground(SystemColor.activeCaption);
	    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnDelete.setBounds(0, 0, 113, 23);
	    panelDelete.add(btnDelete);

	    JPanel panelSearch = new JPanel();
	    panelSearch.setLayout(null);
	    panelSearch.setBounds(630, 130, 113, 23); 
	    btnSearch = new JButton("Search");
	    btnSearch.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Search.png"));
	    btnSearch.setBackground(SystemColor.activeCaption);
	    btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnSearch.setBounds(0, 0, 113, 23);
	    panelSearch.add(btnSearch);

	    JPanel panelRefresh = new JPanel();
	    panelRefresh.setLayout(null);
	    panelRefresh.setBounds(630, 170, 113, 23); 
	    btnRefreshTable = new JButton("Refresh");
	    btnRefreshTable.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\refresh.png"));
	    btnRefreshTable.setBackground(SystemColor.activeCaption);
	    btnRefreshTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnRefreshTable.setBounds(0, 0, 113, 23);
	    panelRefresh.add(btnRefreshTable);
	    
	    JPanel panelExport = new JPanel();
	    panelExport.setLayout(null);
	    panelExport.setBounds(630, 210, 113, 23); 
	    btnExportPDF = new JButton("Export PDF");
	    btnExportPDF.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\pdf-icon.png")); 
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
	    JLabel lblNewLabel = new JLabel("New label");
	    lblNewLabel.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\information about dormitories.png"));
	    lblNewLabel.setBounds(0, 0, 790, 540);
	    add(lblNewLabel);
	}


    
    public void updateContractTable(List<Contract> contracts) {
        contractModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Contract contract : contracts) {
            Object[] rowData = {
                contract.getId(),           // Thêm ID vào đầu
                contract.getRoomNumber(),
                contract.getStudentId(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getMonthlyFee()
            };
            contractModel.addRow(rowData);
        }
    }

    
    public static Date convertToSqlDate(String dateString) {
        try {
            // Định dạng ngày tháng
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter); // Chuyển chuỗi sang LocalDate
            return Date.valueOf(localDate); // Chuyển LocalDate sang java.sql.Date
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu lỗi định dạng
        }
    }
    
    public Contract getContractInput() {
    	String idContract = txtId.getText().trim();
    	String roomNumber = txtRoomNumber.getText().trim();
    	String idStudent = txtStudentId.getText().trim();
    	String startDateInput = txtStartDate.getText().trim();
    	Date startDate = convertToSqlDate(startDateInput);
    	String endDateInput = txtEndDate.getText().trim();
    	Date endDate = convertToSqlDate(endDateInput);
    	double monthlyFee = Double.parseDouble(txtMonthlyFee.getText().trim());
    	return new Contract(idContract, roomNumber, idStudent, startDate, endDate, monthlyFee);
    }
    
    public String getId() {
		return txtId.getText().trim();
	}
    
    public String getRoomNumber() {
		return txtRoomNumber.getText().trim();
	}

	public String getStudentId() {
		return txtStudentId.getText().trim();
	}

	public String getStartDate() {
		return txtStartDate.getText().trim();
    	 
	}

	public String getEndDate() {
		return txtEndDate.getText().trim();
	}

	public double getMonthlyFee() {
		String monthlyFeeText = txtMonthlyFee.getText().trim();
		if(monthlyFeeText.isEmpty()) {
			return 0;
		}
		return Double.parseDouble(txtMonthlyFee.getText().trim());
	}

	
	public JTable getContractTable() {
        return contractTable;
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
    	return btnSearch;
    }
	
	public JButton getRefreshBtn(){
		return btnRefreshTable;
	}

	// phương thức đặt lại giá trị cho các ô nhập liệu
    public void resetInputFields() {
    	txtId.setText("");
    	txtMonthlyFee.setText("");
        txtRoomNumber.setText("");
        txtStudentId.setText("");
        txtMonthlyFee.setText("");
        // không set lại cột ngày tháng vì khi thêm hợp đồng sẽ thường là cùng 1 mốc thời gian
    }
 // Lấy ID hợp đồng được chọn trong bảng
    public String getSelectedContractId() {
        int selectedRow = contractTable.getSelectedRow();
        if (selectedRow != -1) {
            return String.valueOf(contractModel.getValueAt(selectedRow, 0)); // ID hợp đồng nằm ở cột đầu tiên
        }
        return null; // Trả về null nếu không có hàng nào được chọn
    }



	public JButton getBtnExportPDF() {
		return btnExportPDF;
	}

	public void setBtnExportPDF(JButton btnExportPDF) {
		this.btnExportPDF = btnExportPDF;
	}

	public void setContractTable(JTable contractTable) {
		this.contractTable = contractTable;
	}
}
