package service;

import dao.*;
import org.checkerframework.checker.units.qual.A;
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
        try {
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
        } catch (SQLException ex) {
            System.out.println("Error while creating report summary: " + ex.getMessage());
        }
    }

    // Generate a detailed report for all payments
    public void generatePaymentDetailsReport(String startDate, String endDate) {
        try {
            List<Payment> payments = paymentDAO.getPaymentsByDateRange(startDate, endDate);

            System.out.println("\n--- Detailed Payment Report (from " + startDate + " to " + endDate + ") ---");
            if (payments.isEmpty()) {
                System.out.println("No payments found in this date range.");
            } else {
                for (Payment payment : payments) {
                    System.out.println(payment);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while fetching payments: " + ex.getMessage());
        }
    }

    // Generate a detailed report for all buildings assigned to employees
    public void generateBuildingAssignmentReport() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            ApartmentDAO apartmentDAO = new ApartmentDAO();

            System.out.println("\n--- Detailed Building Assignment Report ---");
            for (Employee employee : employees) {
                System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getId() + ")");
                System.out.println("Assigned Buildings: " + employee.getAssignedBuildings().size());

                for (Building building : employee.getAssignedBuildings()) {
                    System.out.println("\tBuilding ID: " + building.getId() + ", Address: " + building.getAddress());

                    // Fetch apartments for the current building
                    List<Apartment> apartments = apartmentDAO.getApartmentsByBuilding(building.getId());

                    if (apartments.isEmpty()) {
                        System.out.println("\t\tNo apartments found in this building.");
                    } else {
                        for (Apartment apartment : apartments) {
                            System.out.println("\t\tApartment ID: " + apartment.getId() +
                                    ", Number: " + apartment.getNumber() +
                                    ", Floor: " + apartment.getFloor() +
                                    ", Area: " + apartment.getArea());

                            // Fetch residents for the apartment
                            List<Resident> residents = residentDAO.getResidentsByApartment(apartment.getId());

                            if (residents.isEmpty()) {
                                System.out.println("\t\t\tNo residents in this apartment.");
                            } else {
                                for (Resident resident : residents) {
                                    System.out.println("\t\t\tResident ID: " + resident.getId() +
                                            ", Name: " + resident.getName() +
                                            ", Age: " + resident.getAge() +
                                            ", Uses Elevator: " + resident.isUsesElevator() +
                                            ", Has Pet: " + resident.isHasPet());
                                }
                            }

                            // Fetch outstanding fees for the apartment
                            double outstandingFees = feeDAO.getOutstandingFeesByApartment(apartment.getId());
                            System.out.println("\t\t\tOutstanding Fees: $" + outstandingFees);

                            // Fetch total payments for the apartment
                            double totalPayments = paymentDAO.getPaymentsByApartment(apartment.getId()).stream().mapToDouble(p -> p.getAmount()).sum();
                            System.out.println("\t\t\tTotal Payments Made: $" + totalPayments);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while generating building report: " + ex.getMessage());
        }
    }
}
