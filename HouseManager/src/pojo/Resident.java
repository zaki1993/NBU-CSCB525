package pojo;

public class Resident {
    private int id;
    private String name;
    private int age;
    private boolean usesElevator;
    private boolean hasPet;
    private int apartmentId;

    // Constructor
    public Resident(int id, String name, int age, boolean usesElevator, boolean hasPet, int apartmentId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
        this.hasPet = hasPet;
        this.apartmentId = apartmentId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isUsesElevator() { return usesElevator; }
    public void setUsesElevator(boolean usesElevator) { this.usesElevator = usesElevator; }

    public boolean isHasPet() { return hasPet; }
    public void setHasPet(boolean hasPet) { this.hasPet = hasPet; }

    public int getApartmentId() { return apartmentId; }  // Getter for apartmentId
    public void setApartmentId(int apartmentId) { this.apartmentId = apartmentId; }  // Setter for apartmentId

    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", usesElevator=" + usesElevator +
                ", hasPet=" + hasPet +
                ", apartmentId=" + apartmentId +
                '}';
    }
}
