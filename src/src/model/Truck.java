package model;

public class Truck extends Vehicle {
    private String fuel;
    private int horsePower;
    private int passengersCapacity;
    private double height;
    private double weight;
    private int cargoCapacity;
    private boolean haveTailLift;

    public Truck(int id, String model, double mileage, String status, double rateData, String fuel, int horsePower, int passengersCapacity, double height, double weight, int cargoCapacity, boolean haveTailLift) {
        super(id, model, mileage, status, rateData);
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.passengersCapacity = passengersCapacity;
        this.height = height;
        this.weight = weight;
        this.cargoCapacity = cargoCapacity;
        this.haveTailLift = haveTailLift;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + getId() +
                ", model='" + getModel() + '\'' +
                ", mileage=" + getMileage() +
                ", status='" + getStatus() + '\'' +
                ", rateData=" + getRateData() +
                ", fuel='" + fuel + '\'' +
                ", horsePower=" + horsePower +
                ", passengersCapacity=" + passengersCapacity +
                ", height=" + height +
                ", weight=" + weight +
                ", cargoCapacity=" + cargoCapacity +
                ", haveTailLift=" + haveTailLift +
                '}';
    }
}
