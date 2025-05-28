package dao;

import model.Contract;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ContractDAO {
    private static Connection connection;

    public ContractDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm hợp đồng mới
    public boolean addContract(Contract contract) throws SQLException {
        String sql = "INSERT INTO contracts (id, room_number, student_id, start_date, end_date, monthly_fee) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, contract.getId());
            statement.setString(2, contract.getRoomNumber());
            statement.setString(3, contract.getStudentId());
            statement.setDate(4, contract.getStartDate());
            statement.setDate(5, contract.getEndDate());
            statement.setDouble(6, contract.getMonthlyFee());

            return statement.executeUpdate() > 0;
        }
    }

    // Lấy danh sách tất cả các hợp đồng
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contracts ORDER BY room_number";
        try (Statement statement = connection.createStatement(); 
        	 ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Contract contract = new Contract(
                		resultSet.getString("id"),
                        resultSet.getString("room_number"),
                        resultSet.getString("student_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getDouble("monthly_fee")
                );
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    // Cập nhật hợp đồng
    public boolean updateContract(Contract contract) {
        String sql = "UPDATE contracts SET room_number = ?, student_id = ?, start_date = ?, end_date = ?, monthly_fee = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contract.getRoomNumber());
            statement.setString(2, contract.getStudentId());
            statement.setDate(3, contract.getStartDate());
            statement.setDate(4, contract.getEndDate());
            statement.setDouble(5, contract.getMonthlyFee());
            statement.setString(6, contract.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Contract getContractById(String contractId) {
        String sql = "SELECT id, room_number, student_id, start_date, end_date, monthly_fee " +
                     "FROM contracts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Gán tham số đúng: contractId, không phải SQL string
            stmt.setString(1, contractId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String id         = rs.getString("id");
                    String roomNumber = rs.getString("room_number");
                    String studentId  = rs.getString("student_id");
                    Date   startDate  = rs.getDate("start_date");
                    Date   endDate    = rs.getDate("end_date");
                    double fee        = rs.getDouble("monthly_fee");

                    return new Contract(
                        id,
                        roomNumber,
                        studentId,
                        startDate,
                        endDate,
                        fee
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading contract: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }


    // Xóa hợp đồng
    public boolean deleteContract(String contractId) {
        String sql = "DELETE FROM contracts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contractId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isRoomExists(String roomNumber) {
        String sql = "SELECT 1 FROM rooms WHERE room_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Trả về true nếu có kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isStudentExists(String string) {
        String sql = "SELECT 1 FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, string);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Trả về true nếu có kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Contract> searchContracts(String contractId, String roomNumber, String studentId, String startDate, String endDate, Double monthlyFee) throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contracts WHERE 1=1";

        if (contractId != null && !contractId.isEmpty()) {
            sql += " AND id LIKE ?";
        }
        if (roomNumber != null && !roomNumber.isEmpty()) {
            sql += " AND room_number LIKE ?";
        }
        if (studentId != null && !studentId.isEmpty()) {
            sql += " AND student_id LIKE ?";
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql += " AND start_date >= ?";
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " AND end_date <= ?";
        }
        if (monthlyFee != 0) {
            sql += " AND monthly_fee = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            if (contractId != null && !contractId.isEmpty()) {
                statement.setString(index++, "%" + contractId + "%");
            }
            if (roomNumber != null && !roomNumber.isEmpty()) {
                statement.setString(index++, "%" + roomNumber + "%");
            }
            if (studentId != null && !studentId.isEmpty()) {
                statement.setString(index++, "%" + studentId + "%");
            }
            if (startDate != null && !startDate.isEmpty()) {
                statement.setString(index++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                statement.setString(index++, endDate);
            }
            if (monthlyFee != 0) {
                statement.setDouble(index++, monthlyFee);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contract contract = new Contract(
                    resultSet.getString("id"),
                    resultSet.getString("room_number"),
                    resultSet.getString("student_id"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getDouble("monthly_fee")
                );
                contracts.add(contract);
            }
        }
        return contracts;
    }

    public String countTotalContracts() {
        String sql = "SELECT COUNT(*) AS total_contracts FROM contracts";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return String.valueOf(rs.getInt("total_contracts"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error retrieving total contracts: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return "0";
    }

}
