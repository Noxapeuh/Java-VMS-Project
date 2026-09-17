package rental;

import model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
public class Fleet implements Iterable<Vehicle>{
    public final List<Vehicle> vehicles = new ArrayList<>();
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
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
            } else {
                return index < size;
            }
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
