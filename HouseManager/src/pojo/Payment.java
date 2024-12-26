package pojo;

import java.sql.Date;

public class Payment {
    private int id;
    private double amount;
    private Date paymentDate;
    private int feeId;
    private int employeeId;
    private int companyId;

    private Apartment apartment;

    // Constructor
    public Payment(int id, double amount, Date paymentDate, int feeId, int employeeId, int companyId) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.feeId = feeId;
        this.employeeId = employeeId;
        this.companyId = companyId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", feeId=" + feeId +
                ", employeeId=" + employeeId +
                ", companyId=" + companyId +
                ", apartment=" + apartment +
                '}';
    }
}
