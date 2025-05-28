package dao;

import model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class RoomDAO {
	private static Connection connection;
	
	public RoomDAO(Connection connection) {
		this.connection = connection;
	}
    // Lấy tất cả phòng
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Room room = new Room(
                	rs.getString("room_number"),
                    rs.getInt("capacity"),
                    rs.getInt("occupied"),
                    rs.getString("status"),
                	rs.getString("room_type"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Thêm phòng
    public boolean addRoom(Room room) {
        String query = "INSERT INTO rooms (room_number, capacity, occupied, status, room_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, room.getRoomNumber());
            pstmt.setInt(2, room.getCapacity());
            pstmt.setInt(3, room.getOccupied());
            pstmt.setString(4, room.getStatus());
            pstmt.setString(5, room.getRoomType());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean canAddStudentToRoom(String roomId, String studentGender) throws SQLException {
        String sql = "SELECT room_type FROM rooms WHERE room_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String roomType = rs.getString("room_type");
                    if ("Male".equalsIgnoreCase(roomType) && !"Male".equalsIgnoreCase(studentGender)) {
                        return false;
                    }
                    if ("Female".equalsIgnoreCase(roomType) && !"Female".equalsIgnoreCase(studentGender)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    // Cập nhật phòng
    public boolean updateRoom(Room room) {
        String query = "UPDATE rooms SET capacity = ?, occupied = ?, status = ?, room_type = ? WHERE room_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        	
        	pstmt.setString(5, room.getRoomNumber());
            pstmt.setInt(1, room.getCapacity());
            pstmt.setInt(2, room.getOccupied());
            pstmt.setString(3, room.getStatus());
            pstmt.setString(4, room.getRoomType());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa phòng
    public boolean deleteRoom(String roomNumber) {
        String query = "DELETE FROM rooms WHERE room_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, roomNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // lấy trạng thái đang sửa chữa của phòng
    public static String getRoomStatus(String roomNumber) {
        String status = null;
        String sql = "SELECT status FROM rooms WHERE room_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public static void updateRoomOccupancy(String roomNumber, int change) {
    	String sql = "UPDATE rooms " +
                "SET occupied = occupied + ?, " +
                "status = CASE " +
                "WHEN occupied + ? >= capacity THEN 'Occupied' " +
                "WHEN occupied + ? < capacity AND occupied + ? > 0 THEN 'Available' " +
                "ELSE 'Maintenance' " + 
                "END " +
                "WHERE room_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setInt(1, change);  // Thay đổi số lượng người ở
        	statement.setInt(2, change);  // Kiểm tra trạng thái "Occupied"
        	statement.setInt(3, change);  // Kiểm tra trạng thái "Available"
        	statement.setInt(4, change);  // Kiểm tra trạng thái "Maintenance"
        	statement.setString(5, roomNumber);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    public static Room getRoomByRoomId(String roomId) {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";
        Room room = null; // Đối tượng Room để trả về
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    room = new Room(
                        resultSet.getString("room_number"),        
                        resultSet.getInt("capacity"),              
                        resultSet.getInt("occupied"),              
                        resultSet.getString("status"),
                        resultSet.getString("room_type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
    
    public static List<Room> searchRooms(String room_number, int capacity, int occupied, String status, String room_type) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE 1=1";

        if (room_number != null && !room_number.isEmpty()) {
            sql += " AND room_number LIKE ?";
        }
        if (capacity > 0) {
            sql += " AND capacity = ?";
        }
        if (occupied > 0) {
            sql += " AND occupied = ?";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }
        if (room_type != null && !room_type.isEmpty()) {
            sql += " AND room_type = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            if (room_number != null && !room_number.isEmpty()) {
                statement.setString(index++, "%" + room_number + "%");
            }
            if (capacity > 0) {
                statement.setInt(index++, capacity);
            }
            if (occupied > 0) {
                statement.setInt(index++, occupied);
            }
            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }
            if (room_type != null && !room_type.isEmpty()) {
                statement.setString(index++, room_type);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(
                        resultSet.getString("room_number"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("occupied"),
                        resultSet.getString("status"),
                        resultSet.getString("room_type")
                    );
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    
    public static int countAvailableRooms() {
        String sql = "SELECT COUNT(*) FROM rooms WHERE status = 'Available'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countOccupiedRooms() {
        String sql = "SELECT COUNT(*) FROM rooms WHERE status = 'Occupied'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countTotalRooms() {
        String sql = "SELECT COUNT(*) AS total_rooms FROM rooms";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_rooms");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error retrieving total rooms: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
    public String countBrokenRooms() {
        String sql = "SELECT COUNT(*) AS total_Maintenance_rooms FROM rooms WHERE status = 'Maintenance'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return String.valueOf(rs.getInt("total_Maintenance_rooms"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error retrieving broken rooms: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return "0";
    }
    
    public int countTotalCapacity() throws SQLException {
        String query = "SELECT SUM(capacity) AS totalCapacity FROM rooms";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalCapacity");
            }
        }
        return 0; // Trả về 0 nếu không có dữ liệu
    }

    // Phương thức đếm tổng số người đang ở của tất cả các phòng
    public int getTotalOccupancy() throws SQLException {
        String query = "SELECT SUM(occupied) AS totalOccupancy FROM rooms";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalOccupancy");
            }
        }
        return 0; // Trả về 0 nếu không có dữ liệu
    }

}
