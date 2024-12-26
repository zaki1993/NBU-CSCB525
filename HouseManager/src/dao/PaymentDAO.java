package dao;

import pojo.Apartment;
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
        String sql = """
        SELECT p.id, p.amount, p.payment_date, p.fee_id, p.employee_id, p.company_id, 
               a.id AS apartment_id, a.number, a.floor, a.building_id, a.area
        FROM payments p
        INNER JOIN fees f ON p.fee_id = f.id
        INNER JOIN apartments a ON f.apartment_id = a.id
        WHERE p.payment_date BETWEEN ? AND ?
        ORDER BY p.payment_date
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

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

                    // Create apartment object
                    Apartment apartment = new Apartment(
                            rs.getInt("apartment_id"),
                            rs.getInt("number"),
                            rs.getInt("floor"),
                            rs.getDouble("area"),
                            rs.getInt("building_id")
                    );

                    // Attach apartment to payment for reporting purposes
                    payment.setApartment(apartment);
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
