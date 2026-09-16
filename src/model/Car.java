package model;

public abstract class Car extends Vehicle {

    private String finition;
    private double topSpeed;
    private double zeroToHundred;
    private String gearbox;
    private int seatCount;
    private int suitcaseCapacity;
    private int doorCount;
    private double co2Emission;

    public Car(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, brand, model, mileage, status, rateData);

        this.finition = finition;
        this.topSpeed = topSpeed;
        this.zeroToHundred = zeroToHundred;
        this.gearbox = gearbox;
        this.seatCount = seatCount;
        this.suitcaseCapacity = suitcaseCapacity;
        this.doorCount = doorCount;
        this.co2Emission = co2Emission;
    }
}