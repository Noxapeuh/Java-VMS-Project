package service;

import contract.PricingPolicy;
import contract.Rentable;
import exception.RentalException;
import model.Customer;
import model.Vehicle;
import rental.Rental;
import rental.Rental.PriceBreakdown;
import repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RentalService {
    private final Repository<Vehicle> vehicleRepository;
    private final Repository<Customer> customerRepository;
    private final List<Rental> rentalHistory = new ArrayList<>();
    private final Set<Integer> activeRentedVehicleIds = new HashSet<>();
    private final Set<Integer> maintenanceDueVehicleIds = new HashSet<>();
    private final Object lock = new Object();

    public RentalService(Repository<Vehicle> vehicleRepository, Repository<Customer> customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public PriceBreakdown calculateQuote(int vehicleId, int period, PricingPolicy policy) {
        Vehicle v = vehicleRepository.getById(vehicleId);
        if (v == null) {
            throw new RentalException("Vehicle not found: " + vehicleId);
        }
        if (period <= 0) {
            throw new RentalException("Rental period must be positive: " + period);
        }
        double total = policy.calculateQuote(v.getRateData(), period);
        return new PriceBreakdown(v.getRateData(), period, policy.getName(), total);
    }

    public Rental rentVehicle(int customerId, int vehicleId, int period, PricingPolicy policy) {
        synchronized (lock) {
            Vehicle v = vehicleRepository.getById(vehicleId);
            if (v == null) {
                throw new RentalException("Vehicle not found: " + vehicleId);
            }
            if (activeRentedVehicleIds.contains(vehicleId) || !"Available".equalsIgnoreCase(v.getStatus())) {
                throw new RentalException("Vehicle is not available for rental: " + vehicleId);
            }
            if (maintenanceDueVehicleIds.contains(vehicleId) || "Maintenance".equalsIgnoreCase(v.getStatus())) {
                throw new RentalException("Vehicle is under maintenance: " + vehicleId);
            }
            Customer c = customerRepository.getById(customerId);
            if (c == null) {
                throw new RentalException("Customer not found: " + customerId);
            }
            if (period <= 0) {
                throw new RentalException("Invalid rental period: " + period);
            }

            PriceBreakdown breakdown = calculateQuote(vehicleId, period, policy);

            if (v instanceof Rentable) {
                ((Rentable) v).rent();
            } else {
                v.setStatus("Rented");
            }

            activeRentedVehicleIds.add(vehicleId);
            String rentalId = "RENT-" + System.currentTimeMillis() + "-" + vehicleId;
            Rental rental = new Rental(rentalId, c, v, period, breakdown, "Active");
            rentalHistory.add(rental);
            return rental;
        }
    }

    public void returnVehicle(String rentalId) {
        synchronized (lock) {
            Rental found = null;
            for (Rental r : rentalHistory) {
                if (r.getRentalId().equals(rentalId) && "Active".equalsIgnoreCase(r.getStatus())) {
                    found = r;
                    break;
                }
            }
            if (found == null) {
                throw new RentalException("Active rental not found with ID: " + rentalId);
            }
            Vehicle v = found.getVehicle();
            if (v instanceof Rentable) {
                ((Rentable) v).returnVehicle();
            } else {
                v.setStatus("Available");
            }
            activeRentedVehicleIds.remove(v.getId());
            found.setStatus("Completed");
        }
    }

    public void markMaintenanceDue(int vehicleId) {
        synchronized (lock) {
            Vehicle v = vehicleRepository.getById(vehicleId);
            if (v == null) {
                throw new RentalException("Vehicle not found: " + vehicleId);
            }
            if (activeRentedVehicleIds.contains(vehicleId)) {
                throw new RentalException("Cannot set maintenance for currently rented vehicle: " + vehicleId);
            }
            maintenanceDueVehicleIds.add(vehicleId);
            v.setStatus("Maintenance");
        }
    }

    public void clearMaintenance(int vehicleId) {
        synchronized (lock) {
            maintenanceDueVehicleIds.remove(vehicleId);
            Vehicle v = vehicleRepository.getById(vehicleId);
            if (v != null) {
                v.setStatus("Available");
            }
        }
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public Set<Integer> getActiveRentedVehicleIds() {
        return activeRentedVehicleIds;
    }

    public Set<Integer> getMaintenanceDueVehicleIds() {
        return maintenanceDueVehicleIds;
    }
}
