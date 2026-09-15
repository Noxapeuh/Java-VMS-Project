package model;

public class Truck extends Vehicle {
    private String fuel;
    private int horsePower;
    private int passengersCapacity;
    private double height;
    private double weight;
    private int cargoCapacity;

    public Truck(int id, String model, double mileage, String status, double rateData, String fuel, int horsePower, int passengersCapacity, double height, double weight, int cargoCapacity) {
        super(id, model, mileage, status, rateData);
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.passengersCapacity = passengersCapacity;
        this.height = height;
        this.weight = weight;
        this.cargoCapacity = cargoCapacity;
    }

}
