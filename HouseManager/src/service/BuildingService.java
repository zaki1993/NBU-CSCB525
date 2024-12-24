package service;

import dao.BuildingDAO;
import pojo.Building;

import java.sql.SQLException;
import java.util.List;

public class BuildingService {
    private final BuildingDAO buildingDAO = new BuildingDAO();

    public BuildingService() throws SQLException {
    }

    public void addBuilding(String address, int floors, int apartments, double totalArea, double sharedArea) {
        try {
            // Update the call to match the new constructor with the 'sharedArea' parameter
            buildingDAO.createBuilding(address, floors, apartments, totalArea, sharedArea);
            System.out.println("pojo.Building added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding building: " + e.getMessage());
        }
    }

    public void editBuilding(int id, String address, int floors, int apartments, double totalArea, double sharedArea) {
        try {
            // Update the call to match the new constructor with the 'sharedArea' parameter
            buildingDAO.updateBuilding(id, address, floors, apartments, totalArea, sharedArea);
            System.out.println("pojo.Building updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating building: " + e.getMessage());
        }
    }

    public void deleteBuilding(int id) {
        try {
            buildingDAO.deleteBuilding(id);
            System.out.println("pojo.Building deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting building: " + e.getMessage());
        }
    }

    public void listBuildings() {
        try {
            List<Building> buildings = buildingDAO.getAllBuildings();
            buildings.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Error retrieving buildings: " + e.getMessage());
        }
    }
}
