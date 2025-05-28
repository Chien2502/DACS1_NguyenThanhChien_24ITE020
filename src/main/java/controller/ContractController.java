package controller;

import dao.ContractDAO;
import dao.DBConnection;
import dao.PdfExporter;
import dao.RoomDAO;
import model.Contract;
import view.ContractManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ContractController {
    private ContractManagementView view;
    private ContractDAO contractDAO;

    public ContractController(ContractManagementView view) throws SQLException {
    	Connection connection = DBConnection.getConnection();
        this.view = view;
        this.contractDAO = new ContractDAO(connection);

        // Attach events to buttons
        this.view.getAddButton().addActionListener(e -> {
			addContract();});
        
        this.view.getEditButton().addActionListener(e -> updateContract());
        this.view.getDeleteButton().addActionListener(e -> deleteContract());
        this.view.getSearchButton().addActionListener(e -> searchContract());
        this.view.getRefreshBtn().addActionListener(e -> loadContractTable());
        this.view.getBtnExportPDF().addActionListener(e -> pdfout());

        // Display initial data
        loadContractTable();
    }

    private void pdfout() {
    	 JFileChooser fileChooser = new JFileChooser();
    	    fileChooser.setDialogTitle("Select location to save file");
    	    int result = fileChooser.showSaveDialog(null);
    	    if (result == JFileChooser.APPROVE_OPTION) {
    	        String path = fileChooser.getSelectedFile().getAbsolutePath();
    	        if (!path.toLowerCase().endsWith(".pdf")) {
    	            path += ".pdf";
    	        }

    	        new PdfExporter().exportTableToPDF(view.getContractTable(), path, "Contarct report");
    	    }
	}

	// Load data into the table
    private void loadContractTable() {
        List<Contract> contracts = contractDAO.getAllContracts();
        view.updateContractTable(contracts);
    }

    // Get information from the table
    private int getSelectedContractId() {
        int selectedRow = view.getContractTable().getSelectedRow();
        if (selectedRow != -1) {
            return (int) view.getContractTable().getValueAt(selectedRow, 0);
        }
        return -1;
    }

    // Add contract
    private void addContract() {
        try {
            // Lấy dữ liệu từ form
            Contract contract = view.getContractInput();

            // Kiểm tra dữ liệu đầu vào
            if (!isValidContract(contract)) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add contract: Please provide all necessary information!", 
                    "Input Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Thêm hợp đồng vào cơ sở dữ liệu
            if (contractDAO.addContract(contract)) {
                JOptionPane.showMessageDialog(view, "Contract added successfully!");
                loadContractTable(); // Làm mới bảng hợp đồng
                view.resetInputFields(); // Xóa trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add contract!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Monthly fee must be a valid number!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view, 
                "Date format is incorrect (yyyy-MM-dd)!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(view, 
                "Contract cannot be added: Duplicate entry or foreign key constraint violation!", 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "A database error occurred: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(view, 
                "Please enter complete and correctly formatted data for start and end dates!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "An unexpected error occurred: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private boolean isValidContract(Contract contract) {
        if (contract == null) {
            JOptionPane.showMessageDialog(view, "Invalid contract data!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check monthly fee
        if (contract.getMonthlyFee() <= 0) {
            JOptionPane.showMessageDialog(view, "Monthly fee must be greater than 0!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check start date and end date
        java.sql.Date startDate = contract.getStartDate();
        java.sql.Date endDate = contract.getEndDate();

        // Convert java.sql.Date to java.time.LocalDate
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();

        if (startLocalDate.isAfter(endLocalDate)) {
            JOptionPane.showMessageDialog(view, "Start date cannot be after end date!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check room ID and student ID in the database
        if (!contractDAO.isRoomExists(contract.getRoomNumber())) {
            JOptionPane.showMessageDialog(view, "Room ID does not exist. Please check again!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!contractDAO.isStudentExists(contract.getStudentId())) {
            JOptionPane.showMessageDialog(view, "Student ID does not exist. Please check again!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true; // Data is valid
    }

    private void updateContract() {
        // 1. Lấy ID hợp đồng đã chọn
        String selectedId = view.getSelectedContractId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view,
                "Please select the contract to edit!",
                "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 2. Lấy đối tượng hiện tại từ DAO
            Contract current = contractDAO.getContractById(selectedId);
            if (current == null) {
                JOptionPane.showMessageDialog(view,
                    "Contract not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Lấy dữ liệu từ form
            Contract input = view.getContractInput();
            // Với các trường để trống, view nên trả về null (Date) hoặc
            // monthlyFee <= 0 để biểu thị “không nhập”.

            // 4. Update khi input hợp lệ
            if (input.getRoomNumber() != null && !input.getRoomNumber().isBlank()) {
                current.setRoomNumber(input.getRoomNumber());
            }
            if (input.getStudentId() != null && !input.getStudentId().isBlank()) {
                current.setStudentId(input.getStudentId());
            }
            if (input.getStartDate() != null) {
                current.setStartDate(input.getStartDate());
            }
            if (input.getEndDate() != null) {
                current.setEndDate(input.getEndDate());
            }
            if (input.getMonthlyFee() > 0) {
                current.setMonthlyFee(input.getMonthlyFee());
            }

            // 5. Validate nếu cần (ví dụ startDate ≤ endDate)
            if (current.getStartDate().after(current.getEndDate())) {
                JOptionPane.showMessageDialog(view,
                    "Start date must be before end date!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 6. Giữ nguyên ID
            current.setId(selectedId);

            // 7. Cập nhật qua DAO
            if (contractDAO.updateContract(current)) {
                JOptionPane.showMessageDialog(view,
                    "Contract updated successfully!");
                loadContractTable();
                view.resetInputFields();
            } else {
                JOptionPane.showMessageDialog(view,
                    "Error updating contract!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view,
                "Date format error! Please enter a valid date (yyyy-MM-dd).",
                "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "An error occurred while updating the contract: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void deleteContract() {
        String contractId = view.getSelectedContractId(); // Get selected contract ID
        if(contractId != null) {
        	int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this contract?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            	if(contractDAO.deleteContract(contractId)) {
            		JOptionPane.showMessageDialog(view, "Contract deleted successfully!");
            		loadContractTable(); // Refresh table
            	}
            	else {
            		JOptionPane.showMessageDialog(view, "Error deleting contract!", "Error", JOptionPane.ERROR_MESSAGE);
            	}
            }
        } else {
        	JOptionPane.showMessageDialog(view, "Please select a contract to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void searchContract(){
        try {
            String idContract = view.getId();
        	String roomNumber = view.getRoomNumber();
        	String idStudent = view.getStudentId();
        	String startDate = view.getStartDate();
        	String endDate = view.getEndDate();
        	double monthlyFee = view.getMonthlyFee();
        	
            List<Contract> result = contractDAO.searchContracts(idContract, roomNumber, idStudent, startDate, endDate, monthlyFee);
            if(result.isEmpty()) {
            	JOptionPane.showMessageDialog(view, "No results were returned!", "Notification", JOptionPane.WARNING_MESSAGE);
            }
            view.updateContractTable(result); // This method is used to display the search results on the table
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error when searching for contracts!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
}
