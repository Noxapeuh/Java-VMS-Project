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

    public String toString() {
        return String.format(
                "Jet [ID=%d, Model=%s, Type=%s, Motor=%s, Passengers=%d, Year=%d, Speed=%d kts, Range=%d nm, Engines=%d, Beds=%d, Wifi=%b, Crew=%b, Airport=%s, Status=%s, Mileage=%.1f, Rate=%.2f]",
                getId(), getModel(), type, motor, passengersCapacity, releaseYear, cruisingSpeed, maxRange, engineCount, bedCount, hasWifi, crewIncluded, baseAirport, getStatus(), getMileage(), getRateData()
        );
    }




}
