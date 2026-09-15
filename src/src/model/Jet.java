package model;
import contract.Rentable;


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
               boolean crewIncluded, String baseAirport){
        super(id, model, mileage, status, rateData);
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
        if (status != "Available"){

        }
    }

}
