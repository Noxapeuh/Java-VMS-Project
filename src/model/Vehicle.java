package model;

import contract.Identifiable;
import java.util.Objects;

public abstract class Vehicle implements Identifiable, Comparable<Vehicle> {
    private final int id;
    private String model;
    private double mileage;
    private String status;
    private double rateData;
    private String brand;

    public Vehicle(int id, String model, double mileage, String status, double rateData, String brand) {
        this.id = id;
        this.model = model;
        this.mileage = mileage;
        this.status = status;
        this.rateData = rateData;
        this.brand = brand;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getMileage() {
        return mileage;
    }

    public String getStatus() {
        return status;
    }

    public double getRateData() {
        return rateData;
    }

    public String getBrand() {
        return brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRateData(double rateData) {
        this.rateData = rateData;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
