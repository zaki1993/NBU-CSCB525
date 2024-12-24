package service;

import dao.ResidentDAO;
import pojo.Resident;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ResidentService {
    private final ResidentDAO residentDAO = new ResidentDAO();

    public ResidentService() throws SQLException {
    }

    public void addResident(String name, int age, boolean usesElevator, boolean hasPet, int apartmentId) {
        try {
            residentDAO.createResident(name, age, usesElevator, hasPet, apartmentId);
            System.out.println("pojo.Resident added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding resident: " + e.getMessage());
        }
    }

    public void editResident(int id, String name, int age, boolean usesElevator, boolean hasPet) {
        try {
            residentDAO.updateResident(id, name, age, usesElevator, hasPet);
            System.out.println("pojo.Resident updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating resident: " + e.getMessage());
        }
    }

    public void deleteResident(int id) {
        try {
            residentDAO.deleteResident(id);
            System.out.println("pojo.Resident deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting resident: " + e.getMessage());
        }
    }

    public void listResidents() {
        try {
            List<Resident> residents = residentDAO.getAllResidents();
            residents.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Error retrieving residents: " + e.getMessage());
        }
    }
    public void sortResidentsByName() throws SQLException {
        List<Resident> residents = residentDAO.getAllResidents();
        residents.sort(Comparator.comparing(Resident::getName));
        residents.forEach(System.out::println);
    }

    public void filterResidentsByBuilding(int buildingId) {
        List<Resident> residents = residentDAO.getResidentsByBuilding(buildingId);
        if (residents.isEmpty()) {
            System.out.println("No residents found in this building.");
        } else {
            residents.forEach(System.out::println);
        }
    }
}
