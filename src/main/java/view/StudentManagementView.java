package view;

import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentManagementView extends JPanel {
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtDob;
    private JRadioButton rdoMale;
    private JRadioButton rdoFemale;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtRoomId;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
	private JButton btnRefresh;
	private ButtonGroup genderGroup;
	private JButton btnExportPDF;


	

	public StudentManagementView() {
	    setBackground(SystemColor.inactiveCaption);

	    // Panel nhập dữ liệu
	    JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
	    inputPanel.setBackground(SystemColor.inactiveCaption);
	    inputPanel.setBounds(10, 11, 568, 250);
	    inputPanel.setBorder(BorderFactory.createTitledBorder("Student information"));
	    
	    inputPanel.add(new JLabel("Student ID:"));
	    txtId = new JTextField();
	    inputPanel.add(txtId);

	    inputPanel.add(new JLabel("Full name:"));
	    txtName = new JTextField();
	    inputPanel.add(txtName);

	    inputPanel.add(new JLabel("Date of birth (YYYY-MM-DD):"));
	    txtDob = new JTextField();
	    inputPanel.add(txtDob);

	    inputPanel.add(new JLabel("Gender:"));
	    JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    genderPanel.setBackground(SystemColor.inactiveCaption);
	    rdoMale = new JRadioButton("Male");
	    rdoMale.setBackground(SystemColor.inactiveCaption);
	    rdoFemale = new JRadioButton("Female");
	    rdoFemale.setBackground(SystemColor.inactiveCaption);
	    genderGroup = new ButtonGroup();
	    genderGroup.add(rdoMale);
	    genderGroup.add(rdoFemale);
	    genderPanel.add(rdoMale);
	    genderPanel.add(rdoFemale);
	    inputPanel.add(genderPanel);

	    inputPanel.add(new JLabel("Phone number:"));
	    txtPhone = new JTextField();
	    inputPanel.add(txtPhone);

	    inputPanel.add(new JLabel("Email:"));
	    txtEmail = new JTextField();
	    inputPanel.add(txtEmail);

	    inputPanel.add(new JLabel("Room ID:"));
	    txtRoomId = new JTextField();
	    inputPanel.add(txtRoomId);

	    // Tạo bảng hiển thị thông tin sinh viên
	    String[] columnNames = {"Student ID", "Full name", "Date of birth", "Gender", "Phone number", "Email", "Room ID"};
	    tableModel = new DefaultTableModel(columnNames, 0);
	    studentTable = new JTable(tableModel);
	    studentTable.setFillsViewportHeight(true);
	    studentTable.setBackground(SystemColor.activeCaption);
	    JScrollPane tableScrollPane = new JScrollPane(studentTable);
	    tableScrollPane.setBounds(10, 267, 770, 262);

	    // Tạo panel riêng biệt cho từng button
	    JPanel addPanel = new JPanel(null);
	    addPanel.setBackground(SystemColor.inactiveCaption);
	    addPanel.setBounds(630, 10, 113, 23);
	    btnAdd = new JButton("Add");
	    btnAdd.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\add icon.png"));
	    btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnAdd.setBackground(SystemColor.activeCaption);
	    btnAdd.setBounds(0, 0, 113, 23);
	    addPanel.add(btnAdd);

	    JPanel editPanel = new JPanel(null);
	    editPanel.setBackground(SystemColor.inactiveCaption);
	    editPanel.setBounds(630, 50, 113, 23);
	    btnEdit = new JButton("Edit");
	    btnEdit.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\edit.png"));
	    btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnEdit.setBackground(SystemColor.activeCaption);
	    btnEdit.setBounds(0, 0, 113, 23);
	    editPanel.add(btnEdit);

	    JPanel deletePanel = new JPanel(null);
	    deletePanel.setBackground(SystemColor.inactiveCaption);
	    deletePanel.setBounds(630, 90, 113, 23);
	    btnDelete = new JButton("Delete");
	    btnDelete.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Delete.png"));
	    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnDelete.setBackground(SystemColor.activeCaption);
	    deletePanel.add(btnDelete);
	    btnDelete.setBounds(0, 0, 113, 23);

	    JPanel searchPanel = new JPanel(null);
	    searchPanel.setBackground(SystemColor.inactiveCaption);
	    searchPanel.setBounds(630, 130, 113, 23);
	    searchButton = new JButton("Search");
	    searchButton.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\Search.png"));
	    searchButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    searchButton.setBackground(SystemColor.activeCaption);
	    searchButton.setBounds(0, 0, 113, 23);
	    searchPanel.add(searchButton);

	    JPanel refreshPanel = new JPanel(null);
	    refreshPanel.setBackground(SystemColor.inactiveCaption);
	    refreshPanel.setBounds(630, 170, 113, 23);
	    btnRefresh = new JButton("Refresh table");
	    btnRefresh.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\refresh.png"));
	    btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    btnRefresh.setBackground(SystemColor.activeCaption);
	    btnRefresh.setBounds(0, 0, 113, 23);
	    refreshPanel.add(btnRefresh);
	    
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
	    setLayout(null);
	    add(inputPanel);       
	    add(tableScrollPane);  
	    add(addPanel);         
	    add(editPanel);      
	    add(deletePanel);      
	    add(searchPanel);    
	    add(refreshPanel);  

	    JLabel lblNewLabel = new JLabel("New label");
	    lblNewLabel.setIcon(new ImageIcon("D:\\picture\\dormitory apps\\information about dormitories.png"));
	    lblNewLabel.setBounds(0, 0, 790, 540);
	    add(lblNewLabel);
	    setVisible(true);
	}


    
	// Phương thức cập nhật bảng danh sách sinh viên
    public void updateStudentTable(List<Student> students) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Student student : students) {
            Object[] rowData = {
                student.getId(),
                student.getName(),
                student.getDob(),
                student.getGender(),
                student.getPhone(),
                student.getEmail(),
                student.getRoomId()
            };
            tableModel.addRow(rowData);
        }
    }
    public static Date convertToSqlDate(String dateString) {
        try {
            // Định dạng ngày tháng
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter); // Chuyển chuỗi sang LocalDate
            return Date.valueOf(localDate); // Chuyển LocalDate sang java.sql.Date
        } catch (DateTimeParseException e) {
           
            return null; // Trả về null nếu lỗi định dạng
        }
    }
    // Lấy thông tin sinh viên từ các ô nhập liệu
    public Student getStudentInput() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String dobInput = txtDob.getText().trim();
        Date dob = convertToSqlDate(dobInput);
        String gender = rdoMale.isSelected() ? "Male" : "Female";
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String roomNumber = txtRoomId.getText().trim();

        return new Student(id, name, dob, gender, phone, email, roomNumber);
    }

 // Lấy ID sinh viên được chọn trong bảng
    public String getSelectedStudentId() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            return String.valueOf(tableModel.getValueAt(selectedRow, 0)); // ID nằm ở cột đầu tiên
        }
        return null; // Trả về null nếu không có hàng nào được chọn
    }
    
    // Các phương thức getter cho nút
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
    
    public JButton getBtnSearch() {
        return searchButton;
    }
    
    public JButton getBtnRefresh() {
		return btnRefresh;
	}
    
    public String getDob() {
    	return txtDob.getText().trim();
    }

    
 public String getTxtId() {
		return txtId.getText().trim();
	}


	public String getTxtName() {
		return txtName.getText().trim();
	}
	
	public String getGender() {
	    if (rdoMale.isSelected()) {
	    	genderGroup.clearSelection();
	        return "Male";
	    } else if (rdoFemale.isSelected()) {
	    	genderGroup.clearSelection();
	        return "Female";
	    } else {
	        return null; // Nếu không có radio nào được chọn
	    }
	}


	public String getTxtPhone() {
		return txtPhone.getText().trim();
	}


	public String getTxtRoomId() {
		return txtRoomId.getText().trim();
	}
	
	public String getTxtEmail() {
		return txtEmail.getText().trim();
	}
	
	public JButton getBtnExportPDF() {
		return btnExportPDF;
	}


	// Các phương thức đặt lại giá trị cho các ô nhập liệu
    public void resetInputFields() {
    	txtId.setText("");
    	txtName.setText("");
    	txtDob.setText("");
    	txtPhone.setText("");
    	txtEmail.setText("");
    	txtRoomId.setText("");
    	genderGroup.clearSelection();
    }

	public String getSelectedRoomNumber() {
		int selectedRow = studentTable.getSelectedRow();
    	if (selectedRow != -1) {
            return String.valueOf(tableModel.getValueAt(selectedRow, 6));
    	}
        return null; // Trả về null nếu không có hàng nào được chọn
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public void setStudentTable(JTable studentTable) {
		this.studentTable = studentTable;
	}
}
