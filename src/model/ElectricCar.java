package model;

public class ElectricCar extends Car {
    private double range;
    private double batteryKw;

    public ElectricCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);
        this.range = 400.0;
        this.batteryKw = 75.0;
    }

    public ElectricCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission, double range, double batteryKw) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);
        this.range = range;
        this.batteryKw = batteryKw;
    }

    public double getRange() {
        return range;
    }

    public double getBatteryKw() {
        return batteryKw;
    }

    @Override
    public String toString() {
        return "ElectricCar{" +
                "id=" + getId() +
                ", brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", mileage=" + getMileage() +
                ", status='" + getStatus() + '\'' +
                ", rateData=" + getRateData() +
                ", range=" + range +
                ", batteryKw=" + batteryKw +
                '}';
    }
}
