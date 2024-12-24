package dao;

import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private final Connection connection = DatabaseConfig.getConnection();

    public ReportDAO() throws SQLException {
    }

    /**
     * Get total collected fees for all companies.
     */
    public List<String> getCompanyIncomeReport() throws SQLException {
        String query = "SELECT c.name, SUM(f.amount_paid) AS total_income " +
                "FROM Company c " +
                "JOIN Building b ON c.id = b.company_id " +
                "JOIN Apartment a ON b.id = a.building_id " +
                "JOIN Fee f ON a.id = f.apartment_id " +
                "GROUP BY c.name " +
                "ORDER BY total_income DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Company: " + rs.getString("name") + ", Total Income: " + rs.getDouble("total_income");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get all employees sorted by name or the number of buildings they manage.
     */
    public List<String> getEmployeeReport(boolean sortByName) throws SQLException {
        String query = sortByName
                ? "SELECT e.name, COUNT(b.id) AS building_count " +
                "FROM Employee e " +
                "LEFT JOIN Building b ON e.id = b.employee_id " +
                "GROUP BY e.id, e.name " +
                "ORDER BY e.name ASC"
                : "SELECT e.name, COUNT(b.id) AS building_count " +
                "FROM Employee e " +
                "LEFT JOIN Building b ON e.id = b.employee_id " +
                "GROUP BY e.id, e.name " +
                "ORDER BY building_count DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Employee: " + rs.getString("name") + ", Buildings Managed: " + rs.getInt("building_count");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get residents in buildings sorted by name or age.
     */
    public List<String> getResidentReport(boolean sortByName) throws SQLException {
        String query = sortByName
                ? "SELECT r.name, r.age, a.id AS apartment_id " +
                "FROM Resident r " +
                "JOIN Apartment a ON r.apartment_id = a.id " +
                "ORDER BY r.name ASC"
                : "SELECT r.name, r.age, a.id AS apartment_id " +
                "FROM Resident r " +
                "JOIN Apartment a ON r.apartment_id = a.id " +
                "ORDER BY r.age DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Resident: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") +
                        ", Apartment ID: " + rs.getInt("apartment_id");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get detailed report of buildings managed by each employee.
     */
    public List<String> getBuildingEmployeeReport() throws SQLException {
        String query = "SELECT e.name AS employee_name, b.address " +
                "FROM Employee e " +
                "JOIN Building b ON e.id = b.employee_id " +
                "ORDER BY e.name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Employee: " + rs.getString("employee_name") +
                        ", Building Address: " + rs.getString("address");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get list of apartments in a given building.
     */
    public List<String> getApartmentReport(int buildingId) throws SQLException {
        String query = "SELECT a.id, a.area, a.floor_number, a.owner_name " +
                "FROM Apartment a " +
                "WHERE a.building_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buildingId);
            ResultSet rs = stmt.executeQuery();

            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Apartment ID: " + rs.getInt("id") +
                        ", Area: " + rs.getDouble("area") +
                        ", Floor: " + rs.getInt("floor_number") +
                        ", Owner: " + rs.getString("owner_name");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get list of residents in a given building.
     */
    public List<String> getResidentBuildingReport(int buildingId) throws SQLException {
        String query = "SELECT r.name, r.age, r.uses_elevator, r.has_pet " +
                "FROM Resident r " +
                "JOIN Apartment a ON r.apartment_id = a.id " +
                "WHERE a.building_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buildingId);
            ResultSet rs = stmt.executeQuery();

            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Resident Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") +
                        ", Uses Elevator: " + rs.getBoolean("uses_elevator") +
                        ", Has Pet: " + rs.getBoolean("has_pet");
                report.add(result);
            }
            return report;
        }
    }

    /**
     * Get summary of fees due or paid.
     */
    public List<String> getFeeSummaryReport(boolean showPaid) throws SQLException {
        String query = showPaid
                ? "SELECT c.name AS company_name, SUM(f.amount_paid) AS total_paid " +
                "FROM Company c " +
                "JOIN Building b ON c.id = b.company_id " +
                "JOIN Apartment a ON b.id = a.building_id " +
                "JOIN Fee f ON a.id = f.apartment_id " +
                "WHERE f.amount_paid IS NOT NULL " +
                "GROUP BY c.name"
                : "SELECT c.name AS company_name, SUM(f.amount) AS total_due " +
                "FROM Company c " +
                "JOIN Building b ON c.id = b.company_id " +
                "JOIN Apartment a ON b.id = a.building_id " +
                "JOIN Fee f ON a.id = f.apartment_id " +
                "WHERE f.amount_paid IS NULL " +
                "GROUP BY c.name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<String> report = new ArrayList<>();
            while (rs.next()) {
                String result = "Company: " + rs.getString("company_name") +
                        (showPaid ? ", Total Paid: " : ", Total Due: ") +
                        rs.getDouble(showPaid ? "total_paid" : "total_due");
                report.add(result);
            }
            return report;
        }
    }
}
