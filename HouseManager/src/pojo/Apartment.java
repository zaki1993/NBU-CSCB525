package pojo;

public class Apartment {
    private int id;
    private int number;
    private int floor;
    private double area;

    private int buildingId; // Reference to the building this apartment belongs to

    public Apartment(int id, int number, int floor, double area, int buildingId) {
        this.id = id;
        this.number = number;
        this.floor = floor;
        this.area = area;
        this.buildingId = buildingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number=" + number +
                ", floor=" + floor +
                ", area=" + area +
                ", buildingId=" + buildingId +
                '}';
    }
}
