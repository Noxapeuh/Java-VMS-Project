package rental;

import model.Vehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fleet implements Iterable<Vehicle> {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    public void addAll(List<Vehicle> newVehicles) {
        if (newVehicles != null) {
            vehicles.addAll(newVehicles);
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public int size() {
        return vehicles.size();
    }

    @Override
    public Iterator<Vehicle> iterator() {
        return vehicles.iterator();
    }
}
