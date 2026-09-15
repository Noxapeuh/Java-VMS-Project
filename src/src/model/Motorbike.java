package model;

public class Motorbike extends Vehicle {
    private String fuel;
    private int fuelCapacity;
    private int horsePower;
    private int passengersCapacity;
    private String type;
    private int cylinders;
    private String requiredLicense;
    private double saddleHeight;
    private boolean hasLuggageCompartment;

    public Motorbike(int id, String model, double mileage, String status, double rateData, String fuel,int fuelCapacity, int horsePower, int passengersCapacity, String type, int cylinders, String requiredLicense, double saddleHeight, boolean hasLuggageCompartment) {
        super(id, model, mileage, status, rateData);
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
    public boolean isRentable(){
        if(getStatus().equals("Available")){

        }
    }

}