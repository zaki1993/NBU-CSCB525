package service;

import dao.ApartmentDAO;
import dao.FeeDAO;
import dao.PaymentDAO;
import pojo.Apartment;
import pojo.Fee;
import pojo.FeeConfigurations;
import pojo.Payment;

import java.sql.*;
import java.util.List;

public class FeeService {
    private final FeeDAO feeDAO;

    public FeeService() throws SQLException {
        feeDAO = new FeeDAO();
    }

    public void createBaseFee(double baseFeePerSqMeter, double elevatorFeePerPerson, double petFee) {
        try {
            feeDAO.createBaseFee(baseFeePerSqMeter, elevatorFeePerPerson, petFee);
            System.out.println("Base fees added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding base fees: " + e.getMessage());
        }
    }

    public void editBaseFee(double baseFeePerSqMeter, double elevatorFeePerPerson, double petFee) {
        try {
            feeDAO.editBaseFee(baseFeePerSqMeter, elevatorFeePerPerson, petFee);
            System.out.println("Base fees updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating base fees: " + e.getMessage());
        }
    }

    public void listBaseFee() {
        try {
            FeeConfigurations feeConfigurations = feeDAO.listBaseFee();
            if (feeConfigurations != null) {
                System.out.println("Base fees: " + feeConfigurations);
            } else {
                System.out.println("No base fees configured");
            }
        } catch (SQLException e) {
            System.err.println("Error listing base fees: " + e.getMessage());
        }
    }

    public void listFees() {
        try {
            List<Fee> fees = feeDAO.listFees();
            if (fees.isEmpty()) {
                System.out.println("No fees found.");
            } else {
                System.out.println("\n--- List of Fees ---");
                for (Fee fee : fees) {
                    System.out.println(fee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listing fees: " + e.getMessage());
        }
    }

    // Method to filter fees by due date
    public void filterFeesByDueDate(String dueDate) {
        List<Fee> fees = feeDAO.filterFeesByDueDate(dueDate);
        if (fees.isEmpty()) {
            System.out.println("No fees found for the provided due date.");
        } else {
            System.out.println("--- List of Fees with Due Date " + dueDate + " ---");
            for (Fee fee : fees) {
                System.out.println(fee);
            }
        }
    }

    public boolean isFeeExist(int feeId) {
        // Check if the fee with the given ID exists
        try {
            return feeDAO.getFeeById(feeId) != null;
        } catch (SQLException e) {
            System.out.println("Error while fetching fee: " + e.getMessage());
        }
        return false;
    }
    public void collectFeesForBuilding(int buildingId, Date dueDate) {
        try {
            ApartmentDAO apartmentDAO = new ApartmentDAO();
            List<Apartment> apartments = apartmentDAO.getApartmentsByBuilding(buildingId);
            if (apartments != null && !apartments.isEmpty()) {
                for (Apartment a : apartments) {
                    if (!feeDAO.isFeeCollectedForMonth(a.getId(), dueDate)) {
                        // Calculate the maintenance fee for the apartment
                        double fee = feeDAO.calculateBuildingMaintenanceFee(a.getId());

                        // Save the fee
                        feeDAO.addFeeForApartment(fee, dueDate, a.getId());

                        // Log the fee
                        System.out.println("Apartment ID: " + a.getId() + ", Fee: " + fee);
                    } else {
                        System.out.println("Apartment ID: " + a.getId() + " already collected the fee!");
                    }
                }
            } else {
                System.out.println("No apartments found in this building!");
            }
        } catch (SQLException e) {
            System.out.println("Collection of fees failed: " + e.getMessage());
        }
    }
}
