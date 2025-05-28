package controller;

import dao.DBConnection;
import dao.PdfExporter;
import dao.RoomDAO;
import model.Room;
import model.Student;
import view.RoomManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomController {
    private static RoomManagementView roomView;
    private RoomDAO roomDAO;
    private Room room;

    private static RoomController instance; // Singleton instance

    private RoomController() {}

    public static RoomController getInstance() {
        if (instance == null) {
            instance = new RoomController();
        }
        return instance;
    }
    
    public RoomController(RoomManagementView roomView) throws SQLException {
        this.roomView = roomView;
        Connection connection = DBConnection.getConnection();
        this.roomDAO = new RoomDAO(connection);

        // Register event for buttons
        this.roomView.getAddButton().addActionListener(e -> addRoom());
        this.roomView.getEditButton().addActionListener(e -> updateRoom());
        this.roomView.getDeleteButton().addActionListener(e -> deleteRoom());
        this.roomView.getSearchButton().addActionListener(e -> searchRoom());
        this.roomView.getBtnRefresh().addActionListener(e -> loadRoomData());
        this.roomView.getBtnExportPDF().addActionListener(e -> pdfout());
        // Display initial data
        loadRoomData();
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

    	        new PdfExporter().exportTableToPDF(roomView.getRoomTable(), path, "Room report");
    	    }
	}

	private void loadRoomData() {
    	 List<Room> room = roomDAO.getAllRooms();
         roomView.updateRoomTable(room);
	}

	// Load room data from the database to the table, \
    // used to update the number of students in the room when adding new students
    public void loadRoomDataforStudent() throws SQLException {
    	Connection connection = DBConnection.getConnection();
    	this.roomDAO = new RoomDAO(connection);
        List<Room> room = roomDAO.getAllRooms();
        roomView.updateRoomTable(room);
    }

    // Handle adding a room
    private void addRoom() {
        String roomNumber = roomView.getRoomNumber();
        int capacity = roomView.getCapacity();
        int occupied = roomView.getOccupied();
        String status = roomView.getStatus();
        String typeRooms = roomView.getRoomTypeInput();
        room = new Room(roomNumber, capacity, occupied, status, typeRooms);
        if (capacity < 0 || occupied < 0) {
            JOptionPane.showMessageDialog(roomView, "Invalid data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (roomDAO.addRoom(room)) {
            JOptionPane.showMessageDialog(roomView, "Room added successfully!");
            loadRoomData();
            roomView.resetInputFields();
        } else {
            JOptionPane.showMessageDialog(roomView, "Failed to add room!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Handle updating room information
    private void updateRoom() {
        // 1. Lấy row đã chọn
        int selectedRow = roomView.getRoomTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(roomView,
                "Please select a room to edit!",
                "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Lấy dữ liệu hiện tại từ table
        TableModel model = roomView.getRoomTable().getModel();
        String currentRoomNumber = model.getValueAt(selectedRow, 0).toString();
        int    currentCapacity   = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());
        int    currentOccupied   = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
        String currentStatus     = model.getValueAt(selectedRow, 3).toString();
        String currentRoomType   = model.getValueAt(selectedRow, 4).toString();

        // 3. Lấy dữ liệu từ view
        String roomNumberInput = roomView.getRoomNumber();      
        String capacityInput   = roomView.getCapacityTxt();   
        String occupiedInput   = roomView.getOccupiedTxt();   
        String statusInput     = roomView.getStatus();         
        String typeInput       = roomView.getRoomTypeInput();

        // 4. Quyết định giá trị mới: nếu view trả về null/empty → lấy giá trị cũ
        String newRoomNumber = (roomNumberInput != null && !roomNumberInput.isBlank())
                               ? roomNumberInput
                               : currentRoomNumber;

        int newCapacity = currentCapacity;
        if (capacityInput != null && !capacityInput.isBlank()) {
            try {
                newCapacity = Integer.parseInt(capacityInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(roomView,
                    "Capacity must be a valid integer!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int newOccupied = currentOccupied;
        if (occupiedInput != null && !occupiedInput.isBlank()) {
            try {
                newOccupied = Integer.parseInt(occupiedInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(roomView,
                    "Occupied must be a valid integer!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String newStatus   = (statusInput != null && !statusInput.isBlank())
                             ? statusInput
                             : currentStatus;
        String newRoomType = (typeInput != null && !typeInput.isBlank())
                             ? typeInput
                             : currentRoomType;

        // 5. Kiểm tra business rule
        if (newCapacity < 0 || newOccupied < 0) {
            JOptionPane.showMessageDialog(roomView,
                "Capacity and Occupied must be non-negative!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (newOccupied > newCapacity) {
            JOptionPane.showMessageDialog(roomView,
                "Occupied cannot exceed Capacity!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 6. Tạo object Room mới
        Room updated = new Room(
            newRoomNumber,
            newCapacity,
            newOccupied,
            newStatus,
            newRoomType
        );

        // 7. Gọi DAO update
        boolean ok = roomDAO.updateRoom(updated);
        if (ok) {
            JOptionPane.showMessageDialog(roomView,
                "Room updated successfully!");
            loadRoomData();              
            roomView.resetInputFields();
        } else {
            JOptionPane.showMessageDialog(roomView,
                "Failed to update room!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Handle deleting a room
    private void deleteRoom() {
        int selectedRow = roomView.getRoomTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(roomView, "Please select a room to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomNumber = (String) roomView.getRoomTable().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(roomView, "Are you sure you want to delete this room?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (roomDAO.deleteRoom(roomNumber)) {
                JOptionPane.showMessageDialog(roomView, "Room deleted successfully!");
                loadRoomData();
            } else {
                JOptionPane.showMessageDialog(roomView, "Failed to delete room!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void searchRoom() {
    	String roomNumber = roomView.getRoomNumber();
        int capacity = roomView.getCapacity();
        int occupied = roomView.getOccupied();
        String status = roomView.getStatus();
        String typeRooms = roomView.getRoomTypeInput();
        List<Room> results = RoomDAO.searchRooms(roomNumber,capacity,occupied,status,typeRooms);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(roomView, "No results found!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            roomView.updateRoomTable(results); // Update the table with the list of results
        }
    }
}
