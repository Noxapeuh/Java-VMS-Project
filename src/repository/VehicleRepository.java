package repository;

import model.*;

public interface VehicleRepository extends Repository<Vehicle> {
    Vehicle getVehicleById(int id);
    Car getCarById(int id);
    Truck getTruckById(int id);
    Motorbike getMotorbikeById(int id);
    Jet getJetById(int id);
    ElectricCar getElectricCarById(int id);
    FuelCar getFuelCarById(int id);
    HybridCar getHybridCarById(int id);
    void addVehicle(Vehicle vehicle);
}
