package service;

import dao.EmployeeDAO;
import dao.FeeDAO;
import pojo.Building;
import pojo.Company;
import pojo.Employee;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService() throws SQLException {
        employeeDAO = new EmployeeDAO();
    }

    public void addEmployee(String name, String phone, String email, int companyId) {
        try {
            employeeDAO.createEmployee(name, phone, email, companyId);
            System.out.println("Employee created successfully!");
        } catch (SQLException ex) {
            System.err.println("Error adding employee: " + ex.getMessage());
        }
    }

    public void editEmployee(int employeeId, String newName, String newPhone, String newEmail, int newCompanyId) {
        employeeDAO.updateEmployee(employeeId, newName, newPhone, newEmail, newCompanyId);
    }

    public void deleteEmployee(int employeeId) {
        try {
            Employee e = employeeDAO.getEmployeeById(employeeId);
            employeeDAO.deleteEmployee(employeeId);
            // Assign the building to new employee
            reassignBuildingsFromEmployee(e);
            System.out.println("Employee terminated and buildings reassigned.");
        } catch (SQLException e) {
            System.err.println(String.format("Error deleting employee with id %d: ", employeeId) + e.getMessage());
        }
    }

    public void listEmployees() {
        try {
            List<Employee> employees = employeeDAO.listEmployees();
            if (employees.isEmpty()) {
                System.out.println("\n--- No employees found! ---");
            } else {
                System.out.println("\n--- List of Employees ---");
                employees.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public void assignBuildingToEmployee(int employeeId, int buildingId) {
        try {
            employeeDAO.assignBuilding(employeeId, buildingId);
            System.out.println("Building assigned successfully.");
        } catch (SQLException e) {
            System.out.println(String.format("Failed to assign building with id %d to employee %d", buildingId, employeeId));
        }
    }

    public void unassignBuildingToEmployee(int buildingId) {
        try {
            employeeDAO.unassignBuilding(buildingId);
            System.out.println("Building unassigned successfully.");
        } catch (SQLException e) {
            System.out.println(String.format("Failed to unassign building with id %d", buildingId));
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
    public boolean isEmployeeExist(int employeeId) {
        // Check if the employee with the given ID exists
        try {
            return employeeDAO.getEmployeeById(employeeId) != null;
        } catch (SQLException e) {
            System.out.println("Error while fetching employee: " + e.getMessage());
        }
        return false;
    }

    public void reassignBuildingsFromEmployee(Employee departingEmployee) {
        for (Building building : departingEmployee.getAssignedBuildings()) {
            // Find the employee with the least buildings
            Employee newEmployee = getEmployeeWithLeastBuildings(departingEmployee.getId());
            // Remove the building from the employee
            unassignBuildingToEmployee(building.getId());
            // Assign the building to the new employee
            if (newEmployee != null) {
                assignBuildingToEmployee(newEmployee.getId(), building.getId());
            }
        }
        System.out.println("Buildings reassigned from employee: " + departingEmployee.getName());
    }

    /**
     * Find the employee with least building excluding the employee which is departing.
     * @param excludeEmployee
     * @return
     */
    public Employee getEmployeeWithLeastBuildings(int excludeEmployee) {
        try {
            return employeeDAO.listEmployees().stream()
                              .filter(e -> e.getId() != excludeEmployee) // exclude the departing employee
                              .min(Comparator.comparingInt(e -> e.getAssignedBuildings().size()))
                              .orElseThrow(() -> new IllegalStateException("No employees available."));
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return null;
    }
}
