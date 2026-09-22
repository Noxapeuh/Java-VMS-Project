package service;

import contract.PricingPolicy;
import exception.RentalException;
import model.Customer;
import model.Vehicle;
import rental.Rental;
import rental.Rental.PriceBreakdown;
import repository.GenericRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RentalService {
    private final GenericRepository<Vehicle> vehicleRepo;
    private final GenericRepository<Customer> customerRepo;
    private final List<Rental> rentalHistory = new ArrayList<>();
    private final Set<Integer> activeRentedVehicleIds = new HashSet<>();

    public RentalService(GenericRepository<Vehicle> vehicleRepo, GenericRepository<Customer> customerRepo) {
        this.vehicleRepo = vehicleRepo;
        this.customerRepo = customerRepo;
    }

    public PriceBreakdown calculateQuote(int vehicleId, int period, PricingPolicy policy) {
        Vehicle v = vehicleRepo.getById(vehicleId);
        if (v == null) throw new RentalException("Vehicle not found");
        if (period <= 0) throw new RentalException("Period must be positive");
        double price = policy.calculateQuote(v.getRateData(), period);
        return new PriceBreakdown(v.getRateData(), period, policy.getName(), price);
    }

    public synchronized Rental rentVehicle(int customerId, int vehicleId, int period, PricingPolicy policy) {
        Vehicle v = vehicleRepo.getById(vehicleId);
        if (v == null || !v.getStatus().equalsIgnoreCase("Available")) {
            throw new RentalException("Vehicle not available");
        }
        Customer c = customerRepo.getById(customerId);
        if (period <= 0) throw new RentalException("Invalid period");

        PriceBreakdown quote = calculateQuote(vehicleId, period, policy);
        v.setStatus("Rented");
        activeRentedVehicleIds.add(vehicleId);

        Rental rental = new Rental("RENT-" + (rentalHistory.size() + 1), c, v, period, quote, "Active");
        rentalHistory.add(rental);
        return rental;
    }

    public synchronized void returnVehicle(String rentalId) {
        for (Rental r : rentalHistory) {
            if (r.getRentalId().equalsIgnoreCase(rentalId) && r.getStatus().equalsIgnoreCase("Active")) {
                r.getVehicle().setStatus("Available");
                activeRentedVehicleIds.remove(r.getVehicle().getId());
                r.setStatus("Completed");
                return;
            }
        }
        throw new RentalException("Rental not found");
    }

    public synchronized void markMaintenanceDue(int vehicleId) {
        Vehicle v = vehicleRepo.getById(vehicleId);
        if (v == null) throw new RentalException("Vehicle not found");
        if (activeRentedVehicleIds.contains(vehicleId)) throw new RentalException("Vehicle is rented");
        v.setStatus("Maintenance");
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public Set<Integer> getActiveRentedVehicleIds() {
        return activeRentedVehicleIds;
    }
}
