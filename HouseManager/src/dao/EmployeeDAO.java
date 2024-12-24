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
    public void createEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO Employee (name, phone, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPhone());
            stmt.setString(3, employee.getEmail());
            stmt.executeUpdate();

            // Retrieve generated ID
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    employee.setId(keys.getInt(1));
                }
            }
        }
    }

    // Retrieve an employee by ID
    public Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM Employee WHERE id = ?";
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
        String query = "SELECT * FROM Employee";
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
    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE Employee SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPhone());
            stmt.setString(3, employee.getEmail());
            stmt.setInt(4, employee.getId());
            stmt.executeUpdate();
        }
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Assign a building to an employee
    public void assignBuilding(int employeeId, int buildingId) throws SQLException {
        String query = "UPDATE Building SET employee_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, buildingId);
            stmt.executeUpdate();
        }
    }

    // Unassign a building from an employee
    public void unassignBuilding(int buildingId) throws SQLException {
        String query = "UPDATE Building SET employee_id = NULL WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, buildingId);
            stmt.executeUpdate();
        }
    }

    // Retrieve buildings assigned to an employee
    private List<Building> getAssignedBuildings(int employeeId) throws SQLException {
        String query = "SELECT * FROM Building WHERE employee_id = ?";
        List<Building> buildings = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                buildings.add(new Building(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getInt("number_of_floors"),
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
}
