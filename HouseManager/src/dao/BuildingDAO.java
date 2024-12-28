package dao;

import pojo.Building;
import pojo.Employee;
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
    public int createBuilding(String address, int floors, int apartments, double totalArea, double sharedArea) throws SQLException {
        String query = "INSERT INTO buildings (address, floors, number_of_apartments, total_area, shared_area) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, address);
            stmt.setInt(2, floors);
            stmt.setInt(3, apartments);
            stmt.setDouble(4, totalArea);
            stmt.setDouble(5, sharedArea);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Return the ID of the newly inserted row
            }
        }
        return -1;
    }

    /**
     * Update an existing building record.
     */
    public void updateBuilding(int id, String address, int floors, int apartments, double totalArea, double sharedArea) throws SQLException {
        String query = "UPDATE buildings SET address = ?, floors = ?, number_of_apartments = ?, total_area = ?, shared_area = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, address);
            stmt.setInt(2, floors);
            stmt.setInt(3, apartments);
            stmt.setDouble(4, totalArea);
            stmt.setDouble(5, sharedArea);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Delete a building record by ID.
     */
    public void deleteBuilding(int id) throws SQLException {
        String query = "DELETE FROM buildings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("No building found with the given ID.");
            }
        }
    }

    /**
     * Retrieve all building records from the database.
     */
    public List<Building> getAllBuildings() throws SQLException {
        String query = "SELECT * FROM buildings";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Building> buildings = new ArrayList<>();
            while (rs.next()) {
                Building b = new Building(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getInt("floors"),
                        rs.getInt("number_of_apartments"),
                        rs.getDouble("total_area"),
                        rs.getDouble("shared_area")
                );
                EmployeeDAO employeeDAO = new EmployeeDAO();
                b.setAssignedEmployee(employeeDAO.getEmployeeById(rs.getInt("employee_id")));
                buildings.add(b);
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

    // Retrieve a building by ID
    public Building getBuildingById(int id) throws SQLException {
        String query = "SELECT * FROM buildings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Building b = new Building(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getInt("floors"),
                        rs.getInt("number_of_apartments"),
                        rs.getDouble("total_area"),
                        rs.getDouble("shared_area")
                );
                return b;
            }
        }
        return null;
    }
}
