package controller;

import dao.DBConnection;
import dao.PdfExporter;
import dao.RoomDAO;
import dao.StudentDAO;
import model.Room;
import model.Student;
import view.StudentManagementView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.List;

public class StudentController {
    private StudentManagementView view;
    private StudentDAO studentDAO;
    private RoomDAO roomDAO;
    public StudentController(StudentManagementView view) throws SQLException {
    	Connection connection = DBConnection.getConnection();
        this.view = view;
        this.studentDAO = new StudentDAO(connection);
        this.roomDAO = new RoomDAO(connection);

        // Khởi tạo dữ liệu
        loadStudentData();

        // Xử lý sự kiện
        view.getBtnAdd().addActionListener(e -> addStudent());
        view.getBtnEdit().addActionListener(e -> updateStudent());
        view.getBtnDelete().addActionListener(e -> deleteStudent());
        view.getBtnSearch().addActionListener(e -> searchStudents());
        view.getBtnRefresh().addActionListener(e -> loadStudentData());
        view.getBtnExportPDF().addActionListener(e -> pdfout());
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

	        new PdfExporter().exportTableToPDF(view.getStudentTable(), path, "Student report");
	    }
	}

	private void searchStudents() {
    	String id = view.getTxtId();
    	String fullName = view.getTxtName();
    	String dob = view.getDob();
    	String gender = view.getGender();
    	String phoneNumber = view.getTxtPhone();
    	String email = view.getTxtEmail();
    	String roomId = view.getTxtRoomId();
    	
        List<Student> results = studentDAO.searchStudents(id, fullName, dob, gender, phoneNumber, email, roomId);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No results found!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.updateStudentTable(results); // Cập nhật bảng với danh sách kết quả
        }
    }

	private void loadStudentData() {
        List<Student> students = studentDAO.getAllStudents();
        view.updateStudentTable(students); // Cập nhật bảng
    }
    
    private boolean checkRoomStatus(String roomNumber) {
        String status = null;
		try {
			status = RoomDAO.getRoomStatus(roomNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Giả định bạn có phương thức này trong RoomDAO
        if ("maintenance".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(view, "The room is experiencing issues and students cannot be added!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    private void addStudent() {
        try {
            // Lấy dữ liệu từ form
            Student student = view.getStudentInput();
            
            // Kiểm tra dữ liệu đầu vào
            if (student == null || student.getId().isEmpty() || student.getRoomId().isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add student: Please provide all required fields!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra trạng thái phòng (maintenance)
            if (!checkRoomStatus(student.getRoomId())) {
                return; // Dừng lại nếu phòng đang bảo trì
            }

            // Kiểm tra loại phòng và giới tính sinh viên
            Room room = RoomDAO.getRoomByRoomId(student.getRoomId());
            if (room == null) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add student: Room does not exist!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!roomDAO.canAddStudentToRoom(student.getRoomId(),student.getGender())) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add student: Room gender restriction not met!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem phòng đã đầy hay chưa
            if (room.getOccupied() >= room.getCapacity()) {
                JOptionPane.showMessageDialog(view, 
                    "The room is full! Cannot add more students.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Thêm sinh viên vào phòng
            if (studentDAO.addStudent(student)) {
                RoomDAO.updateRoomOccupancy(student.getRoomId(), 1); // Tăng số lượng người ở
                JOptionPane.showMessageDialog(view, "Student added successfully!");
                loadStudentData(); // Refresh bảng sinh viên
                RoomController.getInstance().loadRoomDataforStudent(); // Refresh bảng phòng
                view.resetInputFields(); // Reset các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Error adding student!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            // Xử lý các lỗi liên quan đến SQL
            if (e.getMessage().contains("foreign key constraint fails") || e.getMessage().contains("fk_students_rooms")) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add student: Dormitory room does not exist!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("for key 'PRIMARY'")) {
                JOptionPane.showMessageDialog(view, 
                    "An error occurred while adding the student: Student ID already exists!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else if (e.getMessage().contains("Column 'dob' cannot be null")) {
                JOptionPane.showMessageDialog(view, 
                    "Failed to add student: Date of birth input is invalid!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, 
                    "An error occurred while adding the student, please check your input!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace(); // In lỗi ra console để tiện gỡ lỗi

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(view, 
                "Failed to add student: Missing or invalid data!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "An unexpected error occurred while adding the student: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void updateStudent() {
        // 1. Lấy ID đã chọn
        String selectedId = view.getSelectedStudentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view,
                "Please select the student to edit!",
                "Notification", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 2. Lấy đối tượng hiện tại từ DAO (đảm bảo có phương thức này)
            Student current = studentDAO.getStudentById(selectedId);
            if (current == null) {
                JOptionPane.showMessageDialog(view,
                    "Student not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Lấy dữ liệu từ form (có thể null/empty)
            Student input = view.getStudentInput();
            // Ví dụ view.getStudentInput() parse Date, nhưng với field để trống
            // nó trả về null hoặc empty string trong các getter.

            // 4. Chỉ update khi input khác null/empty
            if (input.getName() != null && !input.getName().isBlank()) {
                current.setName(input.getName());
            }
            if (input.getDob() != null) {
                current.setDob(input.getDob());
            }
            if (input.getGender() != null && !input.getGender().isBlank()) {
                current.setGender(input.getGender());
            }
            if (input.getPhone() != null && !input.getPhone().isBlank()) {
                current.setPhone(input.getPhone());
            }
            if (input.getEmail() != null && !input.getEmail().isBlank()) {
                current.setEmail(input.getEmail());
            }
            if (input.getRoomId() != null && !input.getRoomId().isBlank()) {
                current.setRoomId(input.getRoomId());
            }

            // 5. (Tuỳ bạn có business rule nào thêm thì check ở đây)

            // 6. Gán lại ID (dù không đổi, nhưng giữ nhất quán)
            current.setId(selectedId);

            // 7. Thực thi cập nhật
            if (studentDAO.updateStudent(current)) {
                JOptionPane.showMessageDialog(view,
                    "Student updated successfully!");
                loadStudentData();
                view.resetInputFields();
            } else {
                JOptionPane.showMessageDialog(view,
                    "Error updating student!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view,
                "Date format error! Please enter a valid date (yyyy-MM-dd).",
                "Input error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "An error occurred while updating the student: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void deleteStudent() {
        String selectedId = view.getSelectedStudentId(); // Lấy ID từ bảng
        if (selectedId != null) {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (studentDAO.deleteStudent(selectedId)) {
                	RoomDAO.updateRoomOccupancy(view.getSelectedRoomNumber(), -1); // Giảm số lượng người ở
                    JOptionPane.showMessageDialog(view, "Student deleted successfully!");
                    loadStudentData(); // Refresh bảng
					try {
						RoomController.getInstance().loadRoomDataforStudent();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                    JOptionPane.showMessageDialog(view, "Error deleting student!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select the student to delete!", "Notification", JOptionPane.WARNING_MESSAGE);
        }
    }
}
