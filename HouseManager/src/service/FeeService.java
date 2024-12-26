package service;

import dao.FeeDAO;
import dao.PaymentDAO;
import pojo.Fee;
import pojo.Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class FeeService {
    private final FeeDAO feeDAO;

    public FeeService() throws SQLException {
        feeDAO = new FeeDAO();
    }

    public void addFee(double amount, Date dueDate, int apartmentId) {
        try {
            feeDAO.addFee(amount, dueDate, apartmentId);
            System.out.println("Fee added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding fee: " + e.getMessage());
        }
    }

    public void editFee(int feeId, double amount, Date dueDate) {
        try {
            feeDAO.editFee(feeId, amount, dueDate);
            System.out.println("Fee updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating fee: " + e.getMessage());
        }
    }

    public void deleteFee(int feeId) {
        try {
            feeDAO.deleteFee(feeId);
            System.out.println("Fee deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting fee: " + e.getMessage());
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
}
