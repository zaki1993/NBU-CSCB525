package service;

import dao.PaymentDAO;
import pojo.Payment;

import java.sql.Connection;
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

    public void listPayments() {
        List<Payment> payments = paymentDAO.listPayments();
        System.out.println("--- List of Payments ---");
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }
}
