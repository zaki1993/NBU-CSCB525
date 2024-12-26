package dao;

import pojo.Building;
import pojo.Employee;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final Connection connection = DatabaseConfig.getConnection();

    public EmployeeDAO() throws SQLException {
    }

    // Create a new employee
    public void createEmployee(String name, String phone, String email, int companyId) throws SQLException {
        String query = "INSERT INTO employees (name, phone, email, company_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setInt(4, companyId);
            stmt.executeUpdate();
        }
    }

    // Retrieve an employee by ID
    public Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id")
                );
                employee.setAssignedBuildings(getAssignedBuildings(employee.getId()));
                return employee;
            }
        }
        return null;
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id")
                );
                employee.setAssignedBuildings(getAssignedBuildings(employee.getId()));
                employees.add(employee);
            }
            return employees;
        }
    }

    // Update an existing employee
    public void updateEmployee(int employeeId, String newName, String newPhone, String newEmail, int newCompanyId) {
        String sql = "UPDATE employees SET name = ?, phone = ?, email = ?, company_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, newPhone);
            stmt.setString(3, newEmail);
            stmt.setInt(4, newCompanyId);
            stmt.setInt(5, employeeId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("No employee found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error editing employee: " + e.getMessage());
        }
    }

    // Delete an employee by ID
    public void deleteEmployee(int employeeId) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted <= 0) {
                throw new SQLException("No employee found with the given ID.");
            }
        }
    }

    // Assign a building to an employee
    public void assignBuilding(int employeeId, int buildingId) throws SQLException {
        String sql = "UPDATE buildings SET employee_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, buildingId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new SQLException("No building found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error assigning building: " + e.getMessage());
        }
    }

    // Unassign a building from an employee
    public void unassignBuilding(int buildingId) throws SQLException {
        String query = "UPDATE buildings SET employee_id = NULL WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buildingId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated <= 0) {
                throw new SQLException("No building found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error unassigning building: " + e.getMessage());
        }
    }

    // Retrieve buildings assigned to an employee
    private List<Building> getAssignedBuildings(int employeeId) throws SQLException {
        String query = "SELECT * FROM buildings WHERE employee_id = ?";
        List<Building> buildings = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                buildings.add(new Building(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getInt("floors"),
                        rs.getInt("number_of_apartments"),
                        rs.getDouble("total_area"),
                        rs.getDouble("shared_area")
                ));
            }
        }
        return buildings;
    }

    public int getEmployeeCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM employees";
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

    // Method to get employees by company ID
    public List<Employee> getEmployeesByCompany(int companyId) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE company_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the company ID parameter
            stmt.setInt(1, companyId);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getInt("company_id")
                    );
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> listEmployees() throws SQLException {
        String sql = "SELECT e.id, e.name, e.phone, e.email, e.company_id, COUNT(b.id) AS assigned_buildings " +
                "FROM employees e " +
                "LEFT JOIN buildings b ON e.id = b.employee_id " +
                "GROUP BY e.id, e.name, e.phone, e.email";

        List<Employee> employees = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id")
                );
                employee.setAssignedBuildings(getAssignedBuildings(employee.getId()));

                employees.add(employee);
            }
        }
        return employees;
    }
}
