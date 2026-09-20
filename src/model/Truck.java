package model;

import contract.Rentable;
import exception.IsNotRentable;

public class Truck extends Vehicle implements Rentable {
    private String fuel;
    private int horsePower;
    private int passengersCapacity;
    private double height;
    private double weight;
    private int cargoCapacity;
    private boolean haveTailLift;

    public Truck(int id, String model, double mileage, String status, double rateData, String fuel, int horsePower, int passengersCapacity, double height, double weight, int cargoCapacity, boolean haveTailLift, String brand) {
        super(id, model, mileage, status, rateData, brand);
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.passengersCapacity = passengersCapacity;
        this.height = height;
        this.weight = weight;
        this.cargoCapacity = cargoCapacity;
        this.haveTailLift = haveTailLift;
    }

    public String getFuel() {
        return fuel;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public int getPassengersCapacity() {
        return passengersCapacity;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public boolean isHaveTailLift() {
        return haveTailLift;
    }

    @Override
    public boolean isRentable() {
        if (!"Available".equalsIgnoreCase(getStatus())) {
            throw new IsNotRentable("The vehicle can't be rented");
        }
        return true;
    }

    @Override
    public void rent() {
        if (!isRentable()) {
            throw new IsNotRentable("The vehicle is not rentable");
        }
        setStatus("Rented");
    }

    @Override
    public void returnVehicle() {
        setStatus("Available");
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + getId() +
                ", model='" + getModel() + '\'' +
                ", mileage=" + getMileage() +
                ", status='" + getStatus() + '\'' +
                ", rateData=" + getRateData() +
                ", fuel='" + fuel + '\'' +
                ", horsePower=" + horsePower +
                ", passengersCapacity=" + passengersCapacity +
                ", height=" + height +
                ", weight=" + weight +
                ", cargoCapacity=" + cargoCapacity +
                ", haveTailLift=" + haveTailLift +
                '}';
    }
}
