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

}
