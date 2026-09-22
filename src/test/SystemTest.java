package test;

import contract.PricingPolicy;
import exception.RentalException;
import model.Customer;
import model.FuelCar;
import model.Vehicle;
import ordering.VehicleComparators;
import rental.Rental;
import repository.GenericRepository;
import service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("Starting VRMS Automated System Test Harness");

        testValidRental();
        testDoubleBooking();
        testPricingStrategies();
        testMaintenanceBlock();
        testOrderingTieBreaker();
        testConcurrentRequests();

        System.out.println("Test Run Complete: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static Vehicle createCar(int id, double rate) {
        return new FuelCar(id, "Test", "Car", 20000.0, "Available", rate, "Zen", 180.0, 10.0, "Manual", 5, 300, 5, 110.0);
    }

    // Test 1: Valid rental
    public static void testValidRental() {
        GenericRepository<Vehicle> vRepo = new GenericRepository<>();
        GenericRepository<Customer> cRepo = new GenericRepository<>();
        RentalService service = new RentalService(vRepo, cRepo);

        Vehicle car = createCar(101, 50.0);
        vRepo.add(car);
        cRepo.add(new Customer(1, "John", "j@test.com", "123"));

        Rental r = service.rentVehicle(1, 101, 2, new DailyPricingPolicy());

        if (r != null && r.getStatus().equals("Active") && car.getStatus().equalsIgnoreCase("Rented") && service.getActiveRentedVehicleIds().contains(101)) {
            System.out.println("[PASS] Valid rental: one active rental and vehicle status updated");
            passed++;
        } else {
            System.out.println("[FAIL] Valid rental: one active rental and vehicle status updated");
            failed++;
        }
    }

    // Test 2: Double booking rejection
    public static void testDoubleBooking() {
        GenericRepository<Vehicle> vRepo = new GenericRepository<>();
        GenericRepository<Customer> cRepo = new GenericRepository<>();
        RentalService service = new RentalService(vRepo, cRepo);

        vRepo.add(createCar(102, 55.0));
        cRepo.add(new Customer(1, "Alice", "a@test.com", "111"));
        cRepo.add(new Customer(2, "Bob", "b@test.com", "222"));

        service.rentVehicle(1, 102, 1, new DailyPricingPolicy());

        boolean caught = false;
        try {
            service.rentVehicle(2, 102, 1, new DailyPricingPolicy());
        } catch (RentalException e) {
            caught = true;
        }

        if (caught && service.getRentalHistory().size() == 1) {
            System.out.println("[PASS] Double booking: second request rejected with RentalException");
            passed++;
        } else {
            System.out.println("[FAIL] Double booking: second request rejected with RentalException");
            failed++;
        }
    }

    // Test 3: Pricing strategies calculation
    public static void testPricingStrategies() {
        GenericRepository<Vehicle> vRepo = new GenericRepository<>();
        RentalService service = new RentalService(vRepo, new GenericRepository<>());
        vRepo.add(createCar(103, 100.0));

        double daily = service.calculateQuote(103, 2, new DailyPricingPolicy()).getTotalPrice();
        double hourly = service.calculateQuote(103, 2, new HourlyPricingPolicy()).getTotalPrice();
        double weekly = service.calculateQuote(103, 2, new WeeklyPricingPolicy()).getTotalPrice();

        if (Math.abs(daily - 200.0) < 0.01 && Math.abs(hourly - ((100.0 / 24.0) * 2 * 1.15)) < 0.01 && Math.abs(weekly - ((100.0 * 14) * 0.85)) < 0.01) {
            System.out.println("[PASS] Pricing strategies: quotes calculated correctly across policies");
            passed++;
        } else {
            System.out.println("[FAIL] Pricing strategies: quotes calculated correctly across policies");
            failed++;
        }
    }

    // Test 4: Maintenance block
    public static void testMaintenanceBlock() {
        GenericRepository<Vehicle> vRepo = new GenericRepository<>();
        GenericRepository<Customer> cRepo = new GenericRepository<>();
        RentalService service = new RentalService(vRepo, cRepo);

        Vehicle car = createCar(104, 80.0);
        vRepo.add(car);
        cRepo.add(new Customer(1, "Claire", "c@test.com", "333"));

        service.markMaintenanceDue(104);

        boolean caught = false;
        try {
            service.rentVehicle(1, 104, 3, new DailyPricingPolicy());
        } catch (RentalException e) {
            caught = true;
        }

        if (caught && car.getStatus().equalsIgnoreCase("Maintenance") && service.getRentalHistory().isEmpty()) {
            System.out.println("[PASS] Maintenance block: rental rejected without modifying state");
            passed++;
        } else {
            System.out.println("[FAIL] Maintenance block: rental rejected without modifying state");
            failed++;
        }
    }

    // Test 5: Ordering tie breaker
    public static void testOrderingTieBreaker() {
        List<Vehicle> list = new ArrayList<>();
        list.add(createCar(10, 40.0));
        list.add(createCar(5, 40.0));

        Collections.sort(list, VehicleComparators.byRate());

        if (list.get(0).getId() == 5 && list.get(1).getId() == 10) {
            System.out.println("[PASS] Ordering: stable ID tie breaker on equal rates");
            passed++;
        } else {
            System.out.println("[FAIL] Ordering: stable ID tie breaker on equal rates");
            failed++;
        }
    }

    // Test 6: Concurrent requests (Module 9)
    public static void testConcurrentRequests() {
        GenericRepository<Vehicle> vRepo = new GenericRepository<>();
        GenericRepository<Customer> cRepo = new GenericRepository<>();
        RentalService service = new RentalService(vRepo, cRepo);

        Vehicle car = createCar(105, 90.0);
        vRepo.add(car);
        cRepo.add(new Customer(1, "W1", "w1@test.com", "1"));
        cRepo.add(new Customer(2, "W2", "w2@test.com", "2"));

        int[] success = new int[1];
        Thread t1 = new Thread(() -> {
            try {
                service.rentVehicle(1, 105, 1, new DailyPricingPolicy());
                synchronized (success) { success[0]++; }
            } catch (Exception ignored) {}
        });
        Thread t2 = new Thread(() -> {
            try {
                service.rentVehicle(2, 105, 1, new DailyPricingPolicy());
                synchronized (success) { success[0]++; }
            } catch (Exception ignored) {}
        });

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (success[0] == 1 && car.getStatus().equalsIgnoreCase("Rented") && service.getActiveRentedVehicleIds().size() == 1) {
                System.out.println("[PASS] Concurrent requests: exactly one success and consistent final state");
                passed++;
            } else {
                System.out.println("[FAIL] Concurrent requests: exactly one success and consistent final state");
                failed++;
            }
        } catch (InterruptedException e) {
            System.out.println("[FAIL] Concurrent requests interrupted");
            failed++;
        }
    }
}
