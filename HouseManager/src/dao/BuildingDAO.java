package dao;

import pojo.Building;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingDAO {
    private final Connection connection = DatabaseConfig.getConnection();

    public BuildingDAO() throws SQLException {
    }

    /**
     * Create a new building record.
     */
    public void createBuilding(String address, int floors, int apartments, double totalArea, double sharedArea) throws SQLException {
        String query = "INSERT INTO pojo.Building (address, floors, apartments, total_area, shared_area) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, address);
            stmt.setInt(2, floors);
            stmt.setInt(3, apartments);
            stmt.setDouble(4, totalArea);
            stmt.setDouble(5, sharedArea); // Include sharedArea
            stmt.executeUpdate();
        }
    }

    /**
     * Update an existing building record.
     */
    public void updateBuilding(int id, String address, int floors, int apartments, double totalArea, double sharedArea) throws SQLException {
        String query = "UPDATE pojo.Building SET address = ?, floors = ?, apartments = ?, total_area = ?, shared_area = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, address);
            stmt.setInt(2, floors);
            stmt.setInt(3, apartments);
            stmt.setDouble(4, totalArea);
            stmt.setDouble(5, sharedArea); // Include sharedArea
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Delete a building record by ID.
     */
    public void deleteBuilding(int id) throws SQLException {
        String query = "DELETE FROM pojo.Building WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieve all building records from the database.
     */
    public List<Building> getAllBuildings() throws SQLException {
        String query = "SELECT * FROM pojo.Building";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Building> buildings = new ArrayList<>();
            while (rs.next()) {
                // Updated to handle sharedArea as well
                buildings.add(new Building(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getInt("floors"),
                        rs.getInt("apartments"),
                        rs.getDouble("total_area"),
                        rs.getDouble("shared_area") // Map sharedArea
                ));
            }
            return buildings;
        }
    }

    public int getBuildingCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM buildings";
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
