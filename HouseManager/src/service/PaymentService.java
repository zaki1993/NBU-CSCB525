package service;

import dao.PaymentDAO;
import pojo.Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO;

    public PaymentService() throws SQLException {
        this.paymentDAO = new PaymentDAO();
    }

    public void addPayment(double amount, String paymentDate, int feeId, int employeeId, int companyId) {
        try {
            Date date = Date.valueOf(paymentDate);
            paymentDAO.addPayment(amount, date, feeId, employeeId, companyId);
            System.out.println("Payment recorded successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    public void listAllPayments() {
        try {
            List<Payment> payments = paymentDAO.listPayments();
            if (payments.isEmpty()) {
                System.out.println("\n--- No payments found! ---");
            } else {
                System.out.println("--- List of Payments ---");
                for (Payment payment : payments) {
                    System.out.println(payment);
                }
                System.out.println("--- Total Payments ---");
                System.out.println(paymentDAO.getTotalPayments());
            }
        } catch (SQLException ex) {
            System.out.println("Problem while fetching all payments: " + ex.getMessage());
        }
    }

    public void listPaymentsByDateRange(String startDate, String endDate) {
        try {
            List<Payment> payments = paymentDAO.getPaymentsByDateRange(startDate, endDate);
            if (payments.isEmpty()) {
                System.out.println("\n--- No payments found! ---");
            } else {
                System.out.println("\n--- List of Payments ---");
                payments.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            System.out.println("Problem while fetching payments: " + ex.getMessage());
        }
    }

    public void listPaymentsByApartment(int apartmentId) {
        try {
            List<Payment> payments = paymentDAO.getPaymentsByApartment(apartmentId);
            if (payments.isEmpty()) {
                System.out.println("\n--- No payments found! ---");
            } else {
                System.out.println("\n--- List of Payments ---");
                payments.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            System.out.println("Problem while fetching payments: " + ex.getMessage());
        }
    }

    public List<Payment> getPaymentsForApartment(int apartmentId) throws SQLException {
        return paymentDAO.getPaymentsByApartment(apartmentId);
    }
}
