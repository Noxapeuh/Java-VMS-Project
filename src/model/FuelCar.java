package model;

public class FuelCar extends Car{

    private String fuelType;
    private String engineSize;
    private double engineLiter;
    private double consumption;


    public FuelCar(int id, String brand, String model, double mileage, String status, double rateData, String finition, double topSpeed, double zeroToHundred, String gearbox, int seatCount, int suitcaseCapacity, int doorCount, double co2Emission) {
        super(id, brand, model, mileage, status, rateData, finition, topSpeed, zeroToHundred, gearbox, seatCount, suitcaseCapacity, doorCount, co2Emission);

        this.fuelType = fuelType;
        this.engineSize = engineSize;
        this.engineLiter = engineLiter;
        this.consumption = consumption;

    }

    @Override
    public String toString() {
        return "FuelCar{" +
                "id=" + getId() +
                ", brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", mileage=" + getMileage() +
                ", status='" + getStatus() + '\'' +
                ", rateData=" + getRateData() +
                ", fuelType='" + fuelType + '\'' +
                ", engineSize='" + engineSize + '\'' +
                ", engineLiter=" + engineLiter +
                ", consumption=" + consumption +
                '}';
    }
}
