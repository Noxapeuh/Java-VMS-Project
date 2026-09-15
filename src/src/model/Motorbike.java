package model;

public class Motorbike extends Vehicle{
    private String fuel;
    private int horsePower;
    private int passengersCapacity;
    private String type;
    private int cylinders;
    private String requiredLicense;
    private double saddleHeight;
    private boolean hasLuggageCompartment;

    public Motorbike(int id, String model, double mileage, String status, double rateData, String fuel, int horsePower, int passengersCapacity, String type, int cylinders, String requiredLicense, double saddleHeight, boolean hasLuggageCompartment) {
        super(id, model, mileage, status, rateData);
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.passengersCapacity = passengersCapacity;
        this.type = type;
        this.cylinders = cylinders;
        this.requiredLicense = requiredLicense;
        this.saddleHeight = saddleHeight;
        this.hasLuggageCompartment = hasLuggageCompartment;
    }
}
