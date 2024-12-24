package service;

import dao.EmployeeDAO;
import dao.FeeDAO;
import pojo.Building;
import pojo.Employee;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeService {

    private final Connection connection;

    private final EmployeeDAO employeeDAO;

    public EmployeeService() throws SQLException {
        connection = DatabaseConfig.getConnection();
        employeeDAO = new EmployeeDAO();
    }

    public void addEmployee(String name, String phone, String email) {
        String sql = "INSERT INTO employees (name, phone, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("Employee added with ID: " + generatedId);
            }
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    public void editEmployee(int employeeId, String newName, String newPhone, String newEmail) {
        String sql = "UPDATE employees SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, newPhone);
            stmt.setString(3, newEmail);
            stmt.setInt(4, employeeId);

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

    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("No employee found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    public void listEmployees() {
        String sql = "SELECT e.id, e.name, e.phone, e.email, COUNT(b.id) AS assigned_buildings " +
                "FROM employees e " +
                "LEFT JOIN buildings b ON e.id = b.employee_id " +
                "GROUP BY e.id, e.name, e.phone, e.email";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id")
                );
                employee.setAssignedBuildings(new ArrayList<>()); // Will populate with `assignBuilding` logic

                employees.add(employee);

                System.out.printf("ID: %d, Name: %s, Phone: %s, Email: %s, Assigned Buildings: %d%n",
                        employee.getId(), employee.getName(), employee.getPhone(), employee.getEmail(),
                        rs.getInt("assigned_buildings"));
            }

            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            }

        } catch (SQLException e) {
            System.err.println("Error listing employees: " + e.getMessage());
        }
    }

    public void assignBuildingToEmployee(int employeeId, int buildingId) {
        String sql = "UPDATE buildings SET employee_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, buildingId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Building assigned successfully.");
            } else {
                System.out.println("No building found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error assigning building: " + e.getMessage());
        }
    }

    public void sortEmployeesByName() throws SQLException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employees.sort(Comparator.comparing(Employee::getName));
        employees.forEach(System.out::println);
    }

    public void filterEmployeesByCompany(int companyId) {
        List<Employee> employees = employeeDAO.getEmployeesByCompany(companyId);
        if (employees.isEmpty()) {
            System.out.println("No employees found for this company.");
        } else {
            employees.forEach(System.out::println);
        }
    }
}
