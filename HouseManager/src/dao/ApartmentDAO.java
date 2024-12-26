package dao;
import pojo.Apartment;
import pojo.Building;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDAO {
    private Connection connection;

    public ApartmentDAO() throws SQLException {
        this.connection = DatabaseConfig.getConnection();
    }

    // Method to add an apartment
    public void addApartment(int number, int floor, double area, int buildingId) throws SQLException {
        String sql = "INSERT INTO apartments (number, floor, area, building_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, number);
            stmt.setInt(2, floor);
            stmt.setDouble(3, area);
            stmt.setInt(4, buildingId);
            stmt.executeUpdate();
        }
    }

    // Method to update an apartment
    public void updateApartment(int id, int number, int floor, double area, int buildingId) throws SQLException {
        String sql = "UPDATE apartments SET number = ?, floor = ?, area = ?, building_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, number);
            stmt.setInt(2, floor);
            stmt.setDouble(3, area);
            stmt.setInt(4, buildingId);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
    }

    // Method to delete an apartment
    public void deleteApartment(int id) throws SQLException {
        String sql = "DELETE FROM apartments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("No apartment found with the given ID.");
            }
        }
    }

    // Method to get all apartments
    public List<Apartment> getAllApartments() throws SQLException {
        List<Apartment> apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int number = rs.getInt("number");
                int floor = rs.getInt("floor");
                double area = rs.getDouble("area");
                int buildingId = rs.getInt("building_id");
                apartments.add(new Apartment(id, number, floor, area, buildingId));
            }
        }
        return apartments;
    }

    public List<Apartment> getApartmentsByBuilding(int buildingId) throws SQLException {
        List<Apartment> apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartments WHERE building_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, buildingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    apartments.add(new Apartment(
                            rs.getInt("id"),
                            rs.getInt("number"),
                            rs.getInt("floor"),
                            rs.getDouble("area"),
                            buildingId
                    ));
                }
            }
        }
        return apartments;
    }

}
