package pojo;

import java.util.List;

public class Building {
    private int id;
    private String address;
    private int numberOfFloors;
    private int numberOfApartments;
    private double totalArea;
    private double sharedArea;
    private Employee assignedEmployee; // Reference to the employee assigned to the building
    private List<Apartment> apartments; // List of apartments in the building

    // Constructor
    public Building(int id, String address, int numberOfFloors, int numberOfApartments, double totalArea, double sharedArea) {
        this.id = id;
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.numberOfApartments = numberOfApartments;
        this.totalArea = totalArea;
        this.sharedArea = sharedArea;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        if (numberOfFloors <= 0) {
            throw new IllegalArgumentException("Number of floors must be greater than 0.");
        }
        this.numberOfFloors = numberOfFloors;
    }

    public int getNumberOfApartments() {
        return numberOfApartments;
    }

    public void setNumberOfApartments(int numberOfApartments) {
        if (numberOfApartments <= 0) {
            throw new IllegalArgumentException("Number of apartments must be greater than 0.");
        }
        this.numberOfApartments = numberOfApartments;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        if (totalArea <= 0) {
            throw new IllegalArgumentException("Total area must be greater than 0.");
        }
        this.totalArea = totalArea;
    }

    public double getSharedArea() {
        return sharedArea;
    }

    public void setSharedArea(double sharedArea) {
        if (sharedArea < 0) {
            throw new IllegalArgumentException("Shared area cannot be negative.");
        }
        this.sharedArea = sharedArea;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    // Methods to handle apartments and employees

    // Method to add an apartment to the building
    public void addApartment(Apartment apartment) {
        if (apartments != null && !apartments.contains(apartment)) {
            apartments.add(apartment);
        }
    }

    // Method to remove an apartment from the building
    public void removeApartment(Apartment apartment) {
        if (apartments != null) {
            apartments.remove(apartment);
        }
    }

    // Method to assign an employee to the building
    public void assignEmployee(Employee employee) {
        this.assignedEmployee = employee;
    }

    // Method to remove the assigned employee from the building
    public void removeEmployee() {
        this.assignedEmployee = null;
    }

    @Override
    public String toString() {
        String res = "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", numberOfFloors=" + numberOfFloors +
                ", numberOfApartments=" + numberOfApartments +
                ", totalArea=" + totalArea +
                ", sharedArea=" + sharedArea;
        if (assignedEmployee != null) {
            res += ", assignedEmployee=" + (assignedEmployee != null ? assignedEmployee.getName() : "None");
        }
        res += '}';

        return res;
    }
}
