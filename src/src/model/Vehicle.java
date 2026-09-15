package model;

public abstract class Vehicle {
    private int id;
    private String model;
    private double mileage;
    private String status;
    private double rateData;

    public Vehicle(int id, String model, double mileage, String status, double rateData){
        this.id = id;
        this.model = model;
        this.mileage = mileage;
        this.status = status;
        this.rateData = rateData;
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
}
