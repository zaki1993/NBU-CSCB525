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
        String query = "INSERT INTO pojo.Company (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.executeUpdate();
        }
    }

    public void updateCompany(int id, String name, String address, String phone) throws SQLException {
        String query = "UPDATE pojo.Company SET name = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void deleteCompany(int id) throws SQLException {
        String query = "DELETE FROM pojo.Company WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Company> getAllCompanies() throws SQLException {
        String query = "SELECT * FROM pojo.Company";
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
}
