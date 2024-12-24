package service;

import dao.*;
import pojo.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportService {
    private CompanyDAO companyDAO;
    private BuildingDAO buildingDAO;
    private ResidentDAO residentDAO;
    private PaymentDAO paymentDAO;
    private FeeDAO feeDAO;
    private EmployeeDAO employeeDAO;

    public ReportService() throws SQLException {
        this.companyDAO = new CompanyDAO();
        this.buildingDAO = new BuildingDAO();
        this.residentDAO = new ResidentDAO();
        this.paymentDAO = new PaymentDAO();
        this.feeDAO = new FeeDAO();
        this.employeeDAO = new EmployeeDAO();
    }

    // Generate a summary report
    public void generateSummaryReport() {
        int companyCount = companyDAO.getCompanyCount();
        int buildingCount = buildingDAO.getBuildingCount();
        int residentCount = residentDAO.getResidentCount();
        int employeeCount = employeeDAO.getEmployeeCount();
        int feeCount = feeDAO.getFeeCount();
        double totalPayments = paymentDAO.getTotalPayments();

        System.out.println("\n--- Summary Report ---");
        System.out.println("Total Companies: " + companyCount);
        System.out.println("Total Buildings: " + buildingCount);
        System.out.println("Total Residents: " + residentCount);
        System.out.println("Total Employees: " + employeeCount);
        System.out.println("Total Fees: " + feeCount);
        System.out.println("Total Payments Collected: $" + totalPayments);
    }

    // Generate a detailed report for all payments
    public void generatePaymentDetailsReport(String startDate, String endDate) {
        List<Payment> payments = paymentDAO.getPaymentsByDateRange(startDate, endDate);

        System.out.println("\n--- Detailed Payment Report (from " + startDate + " to " + endDate + ") ---");
        if (payments.isEmpty()) {
            System.out.println("No payments found in this date range.");
        } else {
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }

    // Generate a detailed report for all buildings assigned to employees
    public void generateBuildingAssignmentReport() throws SQLException {
        List<Employee> employees = employeeDAO.getAllEmployees();

        System.out.println("\n--- Detailed Building Assignment Report ---");
        for (Employee employee : employees) {
            System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getId() + ")");
            System.out.println("Assigned Buildings: " + employee.getAssignedBuildings().size());
            for (Building building : employee.getAssignedBuildings()) {
                System.out.println("\tBuilding ID: " + building.getId() + ", Address: " + building.getAddress());
            }
        }
    }
}
