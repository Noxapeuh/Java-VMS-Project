package rental;

import model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Fleet implements Iterable<Vehicle> {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    public void addAll(List<? extends Vehicle> newVehicles) {
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
        return new FleetIterator();
    }

    public class FleetIterator implements Iterator<Vehicle> {
        private int index = 0;
        private final int size = vehicles.size();

        @Override
        public boolean hasNext() {
            if (vehicles.size() != size) {
                throw new IllegalStateException("Fleet was modified while iterator is running");
            }
            return index < size;
        }

        @Override
        public Vehicle next() {
            if (!hasNext()) {
                throw new IllegalStateException("Fleet fully iterated");
            }
            return vehicles.get(index++);
        }
    }
}
