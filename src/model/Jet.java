package model;
import contract.Rentable;
import exception.IsNotRentable;


public class Jet extends Vehicle implements Rentable{
    private String type;
    private String motor;
    private int passengersCapacity;
    private int releaseYear;
    private int maxRange;
    private int cruisingSpeed;
    private int engineCount;
    private int bedCount;
    private boolean hasWifi;
    private boolean crewIncluded;
    private String baseAirport;

    public Jet(int id, String model, double mileage, String status, double rateData,String type, String motor, int passengersCapacity, int releaseYear,
               int maxRange, int cruisingSpeed, int engineCount, int bedCount, boolean hasWifi,
               boolean crewIncluded, String baseAirport, String brand){
        super(id, model, mileage, status, rateData, brand);
        this.type = type;
        this.motor = motor;
        this.passengersCapacity = passengersCapacity;
        this.releaseYear = releaseYear;
        this.maxRange = maxRange;
        this.cruisingSpeed = cruisingSpeed;
        this.engineCount = engineCount;
        this.bedCount = bedCount;
        this.hasWifi = hasWifi;
        this.crewIncluded = crewIncluded;
        this.baseAirport = baseAirport;
    }

    @Override
    public boolean isRentable(){
        if (!getStatus().equalsIgnoreCase("Available")){
            throw new IsNotRentable("The vehicle can't be rented");
        }
        return true;
    }

    public String toString(){
        return
                "ID: " + getId() + "\n" +
                        "Model: " + getModel() + "\n" +
                        "Mileage: " + getMileage() + "\n" +
                        "Status: " + getStatus() + "\n" +
                        "Rate Data: " + getRateData() + "\n" +
                        "Type: " + type + "\n" +
                        "Motor: " + motor + "\n" +
                        "Passenger capacity: " + passengersCapacity + "\n" +
                        "Release year: " + releaseYear + "\n" +
                        "Max range: " + maxRange + "\n" +
                        "Cruising speed: " + cruisingSpeed + "\n" +
                        "Number of engines: " + engineCount + "\n" +
                        "Number of beds: " + bedCount + "\n" +
                        "Has wifi: " + hasWifi + "\n" +
                        "Is crew included: " + crewIncluded + "\n" +
                        "Base airport: " + baseAirport + "\n" +
                        "Brand: " + getBrand() + "\n";
    }




}
