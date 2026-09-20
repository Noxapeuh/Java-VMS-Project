package repository;

import model.*;

public class VehicleRepositoryImpl extends GenericRepository<Vehicle> implements VehicleRepository {
    private static final VehicleRepositoryImpl INSTANCE = new VehicleRepositoryImpl();

    private VehicleRepositoryImpl() {
    }

    public static VehicleRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Vehicle getVehicleById(int id) {
        return getById(id);
    }

    @Override
    public Car getCarById(int id) {
        Vehicle v = getById(id);
        return (v instanceof Car) ? (Car) v : null;
    }

    @Override
    public Truck getTruckById(int id) {
        Vehicle v = getById(id);
        return (v instanceof Truck) ? (Truck) v : null;
    }

    @Override
    public Motorbike getMotorbikeById(int id) {
        Vehicle v = getById(id);
        return (v instanceof Motorbike) ? (Motorbike) v : null;
    }

    @Override
    public Jet getJetById(int id) {
        Vehicle v = getById(id);
        return (v instanceof Jet) ? (Jet) v : null;
    }

    @Override
    public ElectricCar getElectricCarById(int id) {
        Vehicle v = getById(id);
        return (v instanceof ElectricCar) ? (ElectricCar) v : null;
    }

    @Override
    public FuelCar getFuelCarById(int id) {
        Vehicle v = getById(id);
        return (v instanceof FuelCar) ? (FuelCar) v : null;
    }

    @Override
    public HybridCar getHybridCarById(int id) {
        Vehicle v = getById(id);
        return (v instanceof HybridCar) ? (HybridCar) v : null;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        add(vehicle);
    }
}
