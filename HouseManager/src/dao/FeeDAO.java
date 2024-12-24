package dao;

import pojo.Fee;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {
    private final Connection connection;

    public FeeDAO() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    public void addFee(double amount, Date dueDate, int apartmentId) throws SQLException {
        String sql = "INSERT INTO fees (amount, due_date, apartment_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setDate(2, dueDate);
            stmt.setInt(3, apartmentId);
            stmt.executeUpdate();
        }
    }

    public void editFee(int feeId, double amount, Date dueDate) throws SQLException {
        String sql = "UPDATE fees SET amount = ?, due_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setDate(2, dueDate);
            stmt.setInt(3, feeId);
            stmt.executeUpdate();
        }
    }

    public void deleteFee(int feeId) throws SQLException {
        String sql = "DELETE FROM fees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feeId);
            stmt.executeUpdate();
        }
    }

    public List<Fee> listFees() throws SQLException {
        String sql = "SELECT * FROM fees";
        List<Fee> fees = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Fee fee = new Fee(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getDate("due_date"),
                        rs.getInt("apartment_id")
                );
                fees.add(fee);
            }
        }
        return fees;
    }

    // Method to filter fees by due date
    public List<Fee> filterFeesByDueDate(String dueDate) {
        List<Fee> fees = new ArrayList<>();
        String sql = "SELECT * FROM fees WHERE due_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dueDate);  // Set the provided due date
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Fee fee = new Fee(
                            rs.getInt("id"),
                            rs.getDouble("amount"),
                            rs.getDate("due_date"),
                            rs.getInt("apartment_id")
                    );
                    fees.add(fee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fees;
    }

    public int getFeeCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM fees";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
