package dao;

import pojo.Resident;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResidentDAO {
    private final Connection connection = DatabaseConfig.getConnection();

    public ResidentDAO() throws SQLException {
    }

    public void createResident(String name, int age, boolean usesElevator, boolean hasPet, int apartmentId) throws SQLException {
        String query = "INSERT INTO residents (name, age, uses_elevator, has_pet, apartment_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setBoolean(3, usesElevator);
            stmt.setBoolean(4, hasPet);
            stmt.setInt(5, apartmentId);
            stmt.executeUpdate();
        }
    }

    public void updateResident(int id, String name, int age, boolean usesElevator, boolean hasPet) throws SQLException {
        String query = "UPDATE residents SET name = ?, age = ?, uses_elevator = ?, has_pet = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setBoolean(3, usesElevator);
            stmt.setBoolean(4, hasPet);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
    }

    public void deleteResident(int id) throws SQLException {
        String query = "DELETE FROM residents WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("No resident found with the given ID.");
            }
        }
    }

    public List<Resident> getAllResidents() throws SQLException {
        String query = "SELECT * FROM residents";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Resident> residents = new ArrayList<>();
            while (rs.next()) {
                residents.add(new Resident(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getBoolean("uses_elevator"), rs.getBoolean("has_pet"), rs.getInt("apartment_id")));
            }
            return residents;
        }
    }

    public int getResidentCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM residents";
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

    // Method to get residents by building ID
    public List<Resident> getResidentsByBuilding(int buildingId) throws SQLException {
        List<Resident> residents = new ArrayList<>();
        String sql = "SELECT r.* FROM residents r " +
                "JOIN apartments a ON r.apartment_id = a.id " +
                "WHERE a.building_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the building ID parameter
            stmt.setInt(1, buildingId);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    residents.add(new Resident(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getBoolean("uses_elevator"),
                            rs.getBoolean("has_pet"),
                            rs.getInt("apartment_id")
                    ));
                }
            }
        }
        return residents;
    }

    public List<Resident> getResidentsByApartment(int apartmentId) throws SQLException {
        List<Resident> residents = new ArrayList<>();
        String sql = "SELECT * FROM residents WHERE apartment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, apartmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    residents.add(new Resident(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getBoolean("uses_elevator"),
                            rs.getBoolean("has_pet"),
                            rs.getInt("apartment_id")
                    ));
                }
            }
        }
        return residents;
    }
}
