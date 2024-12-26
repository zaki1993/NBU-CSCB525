package dao;

import pojo.Payment;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection = DatabaseConfig.getConnection();

    public PaymentDAO() throws SQLException {
    }

    public void addPayment(double amount, Date paymentDate, int feeId, int employeeId, int companyId) {
        String sql = "INSERT INTO payments (amount, payment_date, fee_id, employee_id, company_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setDate(2, paymentDate);
            stmt.setInt(3, feeId);
            stmt.setInt(4, employeeId);
            stmt.setInt(5, companyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> listPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date"),
                        rs.getInt("fee_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("company_id")
                );
                payments.add(payment);
            }
        }
        return payments;
    }

    public double getTotalPayments() throws SQLException {
        double totalPayments = 0;
        String sql = "SELECT SUM(amount) FROM payments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalPayments = rs.getDouble(1);
            }
        }
        return totalPayments;
    }

    // Method to get payments by date range
    public List<Payment> getPaymentsByDateRange(String startDate, String endDate) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE payment_date BETWEEN DATE(?) AND DATE(?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the start and end date parameters
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment(
                            rs.getInt("id"),
                            rs.getDouble("amount"),
                            rs.getDate("payment_date"),
                            rs.getInt("fee_id"),
                            rs.getInt("employee_id"),
                            rs.getInt("company_id")
                    );
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    public List<Payment> getPaymentsByApartment(int apartmentId) throws SQLException {
        String query = "SELECT p.* FROM payments p " +
                "JOIN fees f ON p.fee_id = f.id " +
                "WHERE f.apartment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, apartmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Payment> payments = new ArrayList<>();
                while (resultSet.next()) {
                    payments.add(new Payment(
                            resultSet.getInt("id"),
                            resultSet.getDouble("amount"),
                            resultSet.getDate("payment_date"),
                            resultSet.getInt("fee_id"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("company_id")
                    ));
                }
                return payments;
            }
        }
    }
}
