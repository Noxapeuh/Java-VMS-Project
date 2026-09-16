package model;

public class ElectricCar extends Car{

    private double range;
    private double batteryKw;

    public ElectricCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);

        this.range = range;
        this.batteryKw = batteryKw;

    }
}
