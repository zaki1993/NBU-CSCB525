package pojo;

import java.sql.Date;

public class Fee {
    private int id;
    private double amount;
    private Date dueDate;
    private int apartmentId;

    // Constructor
    public Fee(int id, double amount, Date dueDate, int apartmentId) {
        this.id = id;
        this.amount = amount;
        this.dueDate = dueDate;
        this.apartmentId = apartmentId;
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
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }
        this.dueDate = dueDate;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "id=" + id +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", apartmentId=" + apartmentId +
                '}';
    }
}
