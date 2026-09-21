package model;

import contract.Rentable;
import exception.RentalException;

public class Motorbike extends Vehicle implements Rentable {
    private String fuel;
    private int fuelCapacity;
    private int horsePower;
    private int passengersCapacity;
    private String type;
    private int cylinders;
    private String requiredLicense;
    private double saddleHeight;
    private boolean hasLuggageCompartment;

    public Motorbike(int id, String model, double mileage, String status, double rateData, String fuel, int fuelCapacity, int horsePower, int passengersCapacity, String type, int cylinders, String requiredLicense, double saddleHeight, boolean hasLuggageCompartment, String brand) {
        super(id, model, mileage, status, rateData, brand);
        this.fuel = fuel;
        this.fuelCapacity = fuelCapacity;
        this.horsePower = horsePower;
        this.passengersCapacity = passengersCapacity;
        this.type = type;
        this.cylinders = cylinders;
        this.requiredLicense = requiredLicense;
        this.saddleHeight = saddleHeight;
        this.hasLuggageCompartment = hasLuggageCompartment;
    }

    public String getFuel() {
        return fuel;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public int getPassengersCapacity() {
        return passengersCapacity;
    }

    public String getType() {
        return type;
    }

    public int getCylinders() {
        return cylinders;
    }

    public String getRequiredLicense() {
        return requiredLicense;
    }

    public double getSaddleHeight() {
        return saddleHeight;
    }

    public boolean isHasLuggageCompartment() {
        return hasLuggageCompartment;
    }

    @Override
    public boolean isRentable() {
        if (!getStatus().equalsIgnoreCase("Available")) {
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

    @Override
    public String toString() {
        return "ID: " + getId() + "\n" +
                "Model: " + getModel() + "\n" +
                "Mileage: " + getMileage() + "\n" +
                "Status: " + getStatus() + "\n" +
                "Rate Data: " + getRateData() + "\n" +
                "Type: " + type + "\n" +
                "Fuel: " + fuel + "\n" +
                "Fuel capacity: " + fuelCapacity + "\n" +
                "Horse power: " + horsePower + "\n" +
                "Passenger capacity: " + passengersCapacity + "\n" +
                "Cylinders: " + cylinders + "\n" +
                "Required license: " + requiredLicense + "\n" +
                "Saddle height: " + saddleHeight + "\n" +
                "Has luggage compartment: " + hasLuggageCompartment + "\n" +
                "Brand: " + getBrand() + "\n";
    }
}