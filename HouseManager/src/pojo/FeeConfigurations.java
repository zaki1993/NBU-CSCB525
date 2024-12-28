package pojo;

public class FeeConfigurations {
    private double baseFeePerSqMeter;
    private double elevatorFeePerPerson;
    private double petFee;

    // Constructor
    public FeeConfigurations(double baseFeePerSqMeter, double elevatorFeePerPerson, double petFee) {
        this.baseFeePerSqMeter = baseFeePerSqMeter;
        this.elevatorFeePerPerson = elevatorFeePerPerson;
        this.petFee = petFee;
    }

    // Getters
    public double getBaseFeePerSqMeter() {
        return baseFeePerSqMeter;
    }

    public double getElevatorFeePerPerson() {
        return elevatorFeePerPerson;
    }

    public double getPetFee() {
        return petFee;
    }

    @Override
    public String toString() {
        return "FeeConfigurations{" +
                "baseFeePerSqMeter=" + baseFeePerSqMeter +
                ", elevatorFeePerPerson=" + elevatorFeePerPerson +
                ", petFee=" + petFee +
                '}';
    }
}
