package dao;

import pojo.Company;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    private final Connection connection = DatabaseConfig.getConnection();

    public CompanyDAO() throws SQLException {
    }

    public void createCompany(String name, String address, String phone) throws SQLException {
        String query = "INSERT INTO companies (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.executeUpdate();
        }
    }

    public void updateCompany(int id, String name, String address, String phone) throws SQLException {
        String query = "UPDATE companies SET name = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void deleteCompany(int id) throws SQLException {
        String query = "DELETE FROM companies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("No employee found with the given ID.");
            }
        }
    }

    public List<Company> getAllCompanies() throws SQLException {
        String query = "SELECT * FROM companies";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Company> companies = new ArrayList<>();
            while (rs.next()) {
                companies.add(new Company(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("phone")));
            }
            return companies;
        }
    }

    public int getCompanyCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM companies";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Company getCompanyById(int companyId) throws SQLException {
        Company company = null;

        // SQL query to fetch company by ID
        String sql = "SELECT * FROM companies WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyId);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if we have a result
            if (rs.next()) {
                // Create a Company object from the result set
                company = new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }

        }

        return company; // Returns null if no company is found
    }

    public Company getCompanyByEmployee(int employeeId) throws SQLException {
        String query = "SELECT c.id, c.name, c.address, c.phone " +
                "FROM employees e " +
                "JOIN companies c ON e.company_id = c.id " +
                "WHERE e.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);  // Set the employee ID parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If a company is found, create and return the Company object
                    Company company = new Company(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone")
                    );
                    return company;
                }
            }
        }

        // If no company is found for the given employee ID, return null
        return null;
    }
}
