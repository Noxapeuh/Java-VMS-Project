package model;

import contract.Rentable;
import exception.RentalException;

public abstract class Car extends Vehicle implements Rentable {
    private String finition;
    private double topSpeed;
    private double zeroToHundred;
    private String gearbox;
    private int seatCount;
    private int suitcaseCapacity;
    private int doorCount;
    private double co2Emission;

    public Car(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, model, mileage, status, rateData, brand);
        this.finition = finition;
        this.topSpeed = topSpeed;
        this.zeroToHundred = zeroToHundred;
        this.gearbox = gearbox;
        this.seatCount = seatCount;
        this.suitcaseCapacity = suitcaseCapacity;
        this.doorCount = doorCount;
        this.co2Emission = co2Emission;
    }

    public String getFinition() {
        return finition;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public double getZeroToHundred() {
        return zeroToHundred;
    }

    public String getGearbox() {
        return gearbox;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public int getSuitcaseCapacity() {
        return suitcaseCapacity;
    }

    public int getDoorCount() {
        return doorCount;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    @Override
    public boolean isRentable() {
        if (!"Available".equalsIgnoreCase(getStatus())) {
            throw new RentalException("The vehicle can't be rented");
        }
        return true;
    }

    @Override
    public void rent() {
        if (!isRentable()) {
            throw new RentalException("The vehicle is not rentable");
        }
        setStatus("Rented");
    }

    @Override
    public void returnVehicle() {
        setStatus("Available");
    }
}