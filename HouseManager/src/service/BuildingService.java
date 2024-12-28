package service;

import dao.BuildingDAO;
import pojo.Building;
import pojo.Employee;

import java.sql.SQLException;
import java.util.List;

public class BuildingService {
    private final BuildingDAO buildingDAO = new BuildingDAO();

    public BuildingService() throws SQLException {
    }

    public void addBuilding(String address, int floors, int apartments, double totalArea, double sharedArea) {
        try {
            // Update the call to match the new constructor with the 'sharedArea' parameter
            int newBuildingId = buildingDAO.createBuilding(address, floors, apartments, totalArea, sharedArea);

            // Assign the building to employee
            EmployeeService employeeService = new EmployeeService();
            Employee leastLoadedEmployee = employeeService.getEmployeeWithLeastBuildings(-1);
            employeeService.assignBuildingToEmployee(leastLoadedEmployee.getId(), newBuildingId);

            System.out.println("Building added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding building: " + e.getMessage());
        }
    }

    public void editBuilding(int id, String address, int floors, int apartments, double totalArea, double sharedArea) {
        try {
            // Update the call to match the new constructor with the 'sharedArea' parameter
            buildingDAO.updateBuilding(id, address, floors, apartments, totalArea, sharedArea);
            System.out.println("Building updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating building: " + e.getMessage());
        }
    }

    public void deleteBuilding(int id) {
        try {
            buildingDAO.deleteBuilding(id);
            System.out.println("Building deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting building: " + e.getMessage());
        }
    }

    public void listBuildings() {
        try {
            List<Building> buildings = buildingDAO.getAllBuildings();
            if (buildings.isEmpty()) {
                System.out.println("\n--- No buildings found! ---");
            } else {
                System.out.println("\n--- List of Buildings ---");
                buildings.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving buildings: " + e.getMessage());
        }
    }

    public boolean isBuildingExist(int buildingId) {
        // Check if the building with the given ID exists
        try {
            return buildingDAO.getBuildingById(buildingId) != null;
        } catch (SQLException e) {
            System.out.println("Error while fetching building: " + e.getMessage());
        }
        return false;
    }

    public Building getBuildingById(int buildingId) throws SQLException {
        return buildingDAO.getBuildingById(buildingId);
    }
}
