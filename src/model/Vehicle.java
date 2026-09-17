package model;

public abstract class Vehicle {
    private final int id;
    private String model;
    private double mileage;
    private String status;
    private double rateData;
    private String brand;

    public Vehicle(int id, String model, double mileage, String status, double rateData, String brand){
        if (id < 0) {
            throw new IllegalArgumentException("Vehicle id must be >= 0");
        }
        if (rateData <= 0) {
            throw new IllegalArgumentException("Vehicle rateData must be > 0");
        }
        this.id = id;
        this.model = model;
        this.mileage = mileage;
        this.status = status;
        this.rateData = rateData;
        this.brand = brand;
    }

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

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRateData(double rateData) {
        this.rateData = rateData;
    }

    public String getBrand() {
        return brand;
    }
}
