package report;

import model.Vehicle;
import rental.Fleet;
import rental.Rental;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportService {

    public static List<Vehicle> findEligibleVehicles(Fleet fleet, Predicate<Vehicle> predicate) {
        return fleet.getVehicles().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static List<Vehicle> findMaintenanceDue(Fleet fleet, double mileageThreshold) {
        Predicate<Vehicle> due = v -> v.getMileage() >= mileageThreshold || "Maintenance".equalsIgnoreCase(v.getStatus());
        return fleet.getVehicles().stream()
                .filter(due)
                .collect(Collectors.toList());
    }

    public static double calculateTotalRevenue(List<Rental> rentals) {
        return rentals.stream()
                .filter(r -> r.getPriceBreakdown() != null)
                .mapToDouble(r -> r.getPriceBreakdown().getTotalPrice())
                .sum();
    }

    public static double calculateUtilizationRate(Fleet fleet, Set<Integer> rentedVehicleIds) {
        if (fleet.size() == 0) {
            return 0.0;
        }
        return ((double) rentedVehicleIds.size() / fleet.size()) * 100.0;
    }

    public static List<Vehicle> inspectWithExplicitIterator(Fleet fleet, double mileageThreshold) {
        List<Vehicle> list = new ArrayList<>();
        Iterator<Vehicle> iterator = fleet.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getMileage() >= mileageThreshold) {
                list.add(vehicle);
            }
        }
        return list;
    }
}
