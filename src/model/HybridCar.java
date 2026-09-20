package model;

public class HybridCar extends Car {
    private double range;
    private double batteryKw;
    private boolean rechargeable;
    private String fuelType;
    private String engineSize;
    private double engineLiter;
    private double consumption;

    public HybridCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);
        this.range = 50.0;
        this.batteryKw = 13.0;
        this.rechargeable = true;
        this.fuelType = "Gasoline";
        this.engineSize = "1.6L";
        this.engineLiter = 1.6;
        this.consumption = 3.8;
    }

    public HybridCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission, double range, double batteryKw, boolean rechargeable, String fuelType, String engineSize, double engineLiter, double consumption) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);
        this.range = range;
        this.batteryKw = batteryKw;
        this.rechargeable = rechargeable;
        this.fuelType = fuelType;
        this.engineSize = engineSize;
        this.engineLiter = engineLiter;
        this.consumption = consumption;
    }

    public double getRange() {
        return range;
    }

    public double getBatteryKw() {
        return batteryKw;
    }

    public boolean isRechargeable() {
        return rechargeable;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public double getEngineLiter() {
        return engineLiter;
    }

    public double getConsumption() {
        return consumption;
    }

    @Override
    public String toString() {
        return "HybridCar{" +
                "id=" + getId() +
                ", brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", mileage=" + getMileage() +
                ", status='" + getStatus() + '\'' +
                ", rateData=" + getRateData() +
                ", range=" + range +
                ", batteryKw=" + batteryKw +
                ", rechargeable=" + rechargeable +
                ", fuelType='" + fuelType + '\'' +
                ", engineSize='" + engineSize + '\'' +
                ", engineLiter=" + engineLiter +
                ", consumption=" + consumption +
                '}';
    }
}
