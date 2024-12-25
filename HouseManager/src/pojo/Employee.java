package pojo;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int companyId; // Company ID associated with the employee
    private List<Building> assignedBuildings;

    // Constructor
    public Employee(int id, String name, String phone, String email, int companyId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.companyId = companyId;
        this.assignedBuildings = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public List<Building> getAssignedBuildings() {
        return assignedBuildings;
    }

    public void setAssignedBuildings(List<Building> assignedBuildings) {
        this.assignedBuildings = assignedBuildings;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", companyId=" + companyId +
                ", assignedBuildings=" + assignedBuildings +
                '}';
    }
}
