package repository;

import model.*;

public interface VehicleRepository {
    Vehicle getVehicleById(int id);

    // convenience typed accessors
    Car getCarById(int id);
    Truck getTruckById(int id);
    Motorbike getMotorbikeById(int id);
    Jet getJetById(int id);
    ElectricCar getElectricCarById(int id);
    FuelCar getFuelCarById(int id);
    HybridCar getHybridCarById(int id);

    // allow registering vehicles created elsewhere (e.g., MainApp)
    void addVehicle(Vehicle vehicle);
}
