package pojo;

public class Apartment {
    private int id;
    private String apartmentNumber;
    private double area;
    private String ownerName;
    private Building building; // Reference to the building this apartment belongs to

    // Constructor
    public Apartment(int id, String apartmentNumber, double area, String ownerName, Building building) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.area = area;
        this.ownerName = ownerName;
        this.building = building;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        if (apartmentNumber == null || apartmentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Apartment number cannot be empty.");
        }
        this.apartmentNumber = apartmentNumber;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if (area <= 0) {
            throw new IllegalArgumentException("Area must be greater than 0.");
        }
        this.area = area;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }
        this.ownerName = ownerName;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", area=" + area +
                ", ownerName='" + ownerName + '\'' +
                ", building=" + building.getAddress() +
                '}';
    }
}
