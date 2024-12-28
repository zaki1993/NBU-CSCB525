package dao;

import pojo.Fee;
import pojo.FeeConfigurations;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {
    private final Connection connection;

    public FeeDAO() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    public void addFeeForApartment(double amount, Date dueDate, int apartmentId) throws SQLException {
        String sql = "INSERT INTO fees (amount, due_date, apartment_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setDate(2, dueDate);
            stmt.setInt(3, apartmentId);
            stmt.executeUpdate();
        }
    }

    // Method to create a base fee if it does not already exist
    public void createBaseFee(double baseFeePerSqMeter, double elevatorFeePerPerson, double petFee) throws SQLException {
        try (Statement checkStmt = connection.createStatement();
             ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) AS count FROM fee_configurations")) {
            if (rs.next() && rs.getInt("count") == 0) {
                // Base fee configuration does not exist; insert new record
                try (PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO fee_configurations (base_fee_per_sq_meter, elevator_fee_per_person, pet_fee) VALUES (?, ?, ?)")) {
                    insertStmt.setDouble(1, baseFeePerSqMeter);
                    insertStmt.setDouble(2, elevatorFeePerPerson);
                    insertStmt.setDouble(3, petFee);
                    insertStmt.executeUpdate();
                }
            } else {
                throw new SQLException("Base fee configuration already exists, you can edit it.");
            }
        }
    }

    // Method to edit base fees if they exist
    public void editBaseFee(double baseFeePerSqMeter, double elevatorFeePerPerson, double petFee) throws SQLException {
        String sql = "UPDATE fee_configurations SET base_fee_per_sq_meter = ?, elevator_fee_per_person = ?, pet_fee = ? WHERE id = (SELECT MIN(id) FROM fee_configurations)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, baseFeePerSqMeter);
            stmt.setDouble(2, elevatorFeePerPerson);
            stmt.setDouble(3, petFee);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No base fee configuration found to update.");
            }
        }
    }

    public FeeConfigurations listBaseFee() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM fee_configurations")) {

            while (rs.next()) {
               return new FeeConfigurations(rs.getDouble("base_fee_per_sq_meter"),
                                            rs.getDouble("elevator_fee_per_person"),
                                            rs.getDouble("pet_fee"));
            }
        }
        return null;
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
        String sql = "SELECT * FROM fees WHERE due_date = DATE(?)";
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

    public double getOutstandingFeesByApartment(int apartmentId) throws SQLException {
        String sql = """
                    SELECT 
                        (SELECT SUM(amount) FROM fees WHERE apartment_id = ?) AS total_fees,
                        (SELECT COALESCE(SUM(amount), 0) 
                         FROM payments 
                         WHERE fee_id IN (SELECT id FROM fees WHERE apartment_id = ?)) AS total_payments,
                        (SELECT SUM(amount) FROM fees WHERE apartment_id = ?) - 
                        (SELECT COALESCE(SUM(amount), 0) 
                         FROM payments 
                         WHERE fee_id IN (SELECT id FROM fees WHERE apartment_id = ?)) AS outstanding
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, apartmentId);
            stmt.setInt(2, apartmentId);
            stmt.setInt(3, apartmentId);
            stmt.setInt(4, apartmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("outstanding");
                }
            }
        }
        return 0;
    }

    public FeeConfigurations fetchFeeConfigurations() throws SQLException {
        String query = "SELECT base_fee_per_sq_meter, elevator_fee_per_person, pet_fee FROM fee_configurations LIMIT 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                double baseFee = rs.getDouble("base_fee_per_sq_meter");
                double elevatorFee = rs.getDouble("elevator_fee_per_person");
                double petFee = rs.getDouble("pet_fee");

                return new FeeConfigurations(baseFee, elevatorFee, petFee);
            }
        }

        throw new SQLException("Fee configuration not found");
    }

    public Fee getFeeById(int feeId) throws SQLException {
        Fee fee = null;

        String sql = "SELECT * FROM fees WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feeId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if we have a result
            if (rs.next()) {
                fee = new Fee(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getDate("due_date"),
                        rs.getInt("apartment_id")
                );
            }

        }

        return fee;
    }
    public double calculateBuildingMaintenanceFee(int apartmentId) throws SQLException {
        FeeConfigurations feeConfigurations = fetchFeeConfigurations();
        if (feeConfigurations == null) {
            throw new SQLException("Base fees are not set!");
        }
        // Base rates
        double ratePerSqMeter = feeConfigurations.getBaseFeePerSqMeter();
        double elevatorFee = feeConfigurations.getElevatorFeePerPerson();
        double petFee = feeConfigurations.getPetFee();

        double totalFee = 0.0;

        // Fetch apartment details
        try (PreparedStatement apartmentStmt = connection.prepareStatement("SELECT area FROM apartments WHERE id = ?")) {
            apartmentStmt.setInt(1, apartmentId);
            ResultSet apartmentRs = apartmentStmt.executeQuery();

            if (apartmentRs.next()) {
                double apartmentSize = apartmentRs.getDouble("area");

                // Base fee based on apartment size
                totalFee += apartmentSize * ratePerSqMeter;
            } else {
                throw new SQLException("Apartment not found with ID: " + apartmentId);
            }
        }

        // Fetch resident details and calculate additional fees
        try (PreparedStatement residentsStmt = connection.prepareStatement("""
                                                                                    SELECT age, uses_elevator, has_pet
                                                                                    FROM residents
                                                                                    WHERE apartment_id = ?;
                                                                                """)) {
            residentsStmt.setInt(1, apartmentId);
            ResultSet residentsRs = residentsStmt.executeQuery();

            while (residentsRs.next()) {
                int age = residentsRs.getInt("age");
                boolean usesElevator = residentsRs.getBoolean("uses_elevator");
                boolean hasPet = residentsRs.getBoolean("has_pet");

                // Additional fee for residents over 7 years old who use the elevator
                if (age > 7 && usesElevator) {
                    totalFee += elevatorFee;
                }
                // Additional fee for the pet
                if (hasPet) {
                    totalFee += petFee;
                }
            }
        }

        return totalFee;
    }

    public boolean isFeeCollectedForMonth(int apartmentId, Date dueDate) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM fees WHERE apartment_id = ? AND date_trunc('month', due_date::timestamp) = date_trunc('month', ?::timestamp)")) {
            stmt.setInt(1, apartmentId);
            stmt.setDate(2, dueDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if at least one record exists
                }
            }
        }
        return false;
    }

    public List<Fee> getUnpaidFeesByApartment(int apartmentId) throws SQLException {
        String query = "SELECT * FROM fees WHERE apartment_id = ? AND amount > " +
                "(SELECT COALESCE(SUM(amount), 0) FROM payments WHERE fee_id = fees.id)";
        List<Fee> unpaidFees = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, apartmentId);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Fee fee = new Fee(
                            rs.getInt("id"),
                            rs.getDouble("amount"),
                            rs.getDate("due_date"),
                            rs.getInt("apartment_id")
                    );
                    unpaidFees.add(fee);
                }
            }
        }
        return unpaidFees;
    }
}
