package service;

import dao.ApartmentDAO;
import pojo.Apartment;

import java.sql.SQLException;
import java.util.List;

public class ApartmentService {
    private ApartmentDAO apartmentDAO;

    public ApartmentService() throws SQLException {
        this.apartmentDAO = new ApartmentDAO();
    }

    public void addApartment(int number, int floor, double area, int buildingId) {
        try {
            apartmentDAO.addApartment(number, floor, area, buildingId);
            System.out.println("Apartment added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding apartment: " + e.getMessage());
        }
    }

    public void updateApartment(int id, int number, int floor, double area, int buildingId) {
        try {
            apartmentDAO.updateApartment(id, number, floor, area, buildingId);
            System.out.println("Apartment updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating apartment: " + e.getMessage());
        }
    }

    public void deleteApartment(int id) {
        try {
            apartmentDAO.deleteApartment(id);
            System.out.println("Apartment deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting apartment: " + e.getMessage());
        }
    }

    public void listAllApartments() {
        try {
            List<Apartment> apartments = apartmentDAO.getAllApartments();
            if (apartments.isEmpty()) {
                System.out.println("\n--- No apartments found! ---");
            } else {
                System.out.println("\n--- List of Apartments ---");
                apartments.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error listing apartments: " + e.getMessage());
        }
    }

    public boolean isApartmentExist(int apartmentId) {
        // Check if the apartment with the given ID exists
        try {
            return apartmentDAO.getApartmentById(apartmentId) != null;
        } catch (SQLException e) {
            System.out.println("Error while fetching apartment: " + e.getMessage());
        }
        return false;
    }

    public Apartment getApartmentById(int apartmentId) throws SQLException {
        return apartmentDAO.getApartmentById(apartmentId);
    }
}
