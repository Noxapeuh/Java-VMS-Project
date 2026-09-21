package test;

import contract.PricingPolicy;
import exception.RentalException;
import model.Customer;
import model.FuelCar;
import model.Vehicle;
import ordering.VehicleComparators;
import rental.Rental;
import rental.Rental.PriceBreakdown;
import repository.GenericRepository;
import repository.Repository;
import service.DailyPricingPolicy;
import service.HourlyPricingPolicy;
import service.RentalService;
import service.WeeklyPricingPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemTest {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("Starting VRMS Automated System Test Harness");

        testValidRental();
        testDoubleBooking();
        testPricingStrategies();
        testMaintenanceBlock();
        testOrderingTieBreaker();
        testConcurrentRequests();

        System.out.println("Test Run Complete: " + testsPassed + " passed, " + testsFailed + " failed.");
        if (testsFailed > 0) {
            System.exit(1);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            testsPassed++;
        } else {
            System.out.println("[FAIL] " + testName);
            testsFailed++;
        }
    }

    public static void testValidRental() {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService service = new RentalService(vehicleRepo, customerRepo);

        Vehicle car = new FuelCar(101, "Renault", "Clio", 20000.0, "Available", 50.0, "Zen", 180.0, 10.0, "Manual", 5, 300, 5, 110.0);
        Customer customer = new Customer(1, "John Doe", "john@test.com", "0123456789");
        vehicleRepo.add(car);
        customerRepo.add(customer);

        Rental rental = service.rentVehicle(1, 101, 2, new DailyPricingPolicy());

        boolean validRental = rental != null && "Active".equals(rental.getStatus());
        boolean vehicleUnavailable = "Rented".equalsIgnoreCase(car.getStatus());
        boolean inActiveSet = service.getActiveRentedVehicleIds().contains(101);

        assertTrue("Valid rental: one active rental and vehicle status updated", validRental && vehicleUnavailable && inActiveSet);
    }

    public static void testDoubleBooking() {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService service = new RentalService(vehicleRepo, customerRepo);

        Vehicle car = new FuelCar(102, "Peugeot", "208", 30000.0, "Available", 55.0, "Allure", 185.0, 9.8, "Manual", 5, 310, 5, 115.0);
        Customer c1 = new Customer(1, "Alice", "alice@test.com", "111");
        Customer c2 = new Customer(2, "Bob", "bob@test.com", "222");
        vehicleRepo.add(car);
        customerRepo.add(c1);
        customerRepo.add(c2);

        service.rentVehicle(1, 102, 1, new DailyPricingPolicy());

        boolean exceptionCaught = false;
        try {
            service.rentVehicle(2, 102, 1, new DailyPricingPolicy());
        } catch (RentalException e) {
            exceptionCaught = true;
        }

        boolean historyHasOnlyOne = service.getRentalHistory().size() == 1;

        assertTrue("Double booking: second request rejected with RentalException", exceptionCaught && historyHasOnlyOne);
    }

    public static void testPricingStrategies() {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService service = new RentalService(vehicleRepo, customerRepo);

        Vehicle car = new FuelCar(103, "Audi", "A3", 15000.0, "Available", 100.0, "S-Line", 220.0, 7.5, "Auto", 5, 380, 5, 130.0);
        vehicleRepo.add(car);

        PricingPolicy daily = new DailyPricingPolicy();
        PricingPolicy hourly = new HourlyPricingPolicy();
        PricingPolicy weekly = new WeeklyPricingPolicy();

        PriceBreakdown qDaily = service.calculateQuote(103, 2, daily);
        PriceBreakdown qHourly = service.calculateQuote(103, 2, hourly);
        PriceBreakdown qWeekly = service.calculateQuote(103, 2, weekly);

        boolean dailyCorrect = Math.abs(qDaily.getTotalPrice() - 200.0) < 0.001;
        boolean hourlyCorrect = Math.abs(qHourly.getTotalPrice() - ((100.0 / 24.0) * 2 * 1.15)) < 0.001;
        boolean weeklyCorrect = Math.abs(qWeekly.getTotalPrice() - ((100.0 * 14) * 0.85)) < 0.001;

        assertTrue("Pricing strategies: quotes calculated correctly across policies", dailyCorrect && hourlyCorrect && weeklyCorrect);
    }

    public static void testMaintenanceBlock() {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService service = new RentalService(vehicleRepo, customerRepo);

        Vehicle car = new FuelCar(104, "BMW", "118i", 95000.0, "Available", 80.0, "M-Sport", 215.0, 8.2, "Auto", 5, 360, 5, 125.0);
        Customer customer = new Customer(1, "Claire", "claire@test.com", "333");
        vehicleRepo.add(car);
        customerRepo.add(customer);

        service.markMaintenanceDue(104);

        boolean rejected = false;
        try {
            service.rentVehicle(1, 104, 3, new DailyPricingPolicy());
        } catch (RentalException e) {
            rejected = true;
        }

        boolean stateUnchanged = "Maintenance".equalsIgnoreCase(car.getStatus()) && service.getRentalHistory().isEmpty();

        assertTrue("Maintenance block: rental rejected without modifying state", rejected && stateUnchanged);
    }

    public static void testOrderingTieBreaker() {
        Vehicle v1 = new FuelCar(10, "Citroen", "C3", 50000.0, "Available", 40.0, "Feel", 170.0, 11.0, "Manual", 5, 300, 5, 105.0);
        Vehicle v2 = new FuelCar(5, "Citroen", "C3", 50000.0, "Available", 40.0, "Feel", 170.0, 11.0, "Manual", 5, 300, 5, 105.0);

        List<Vehicle> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);

        Collections.sort(list, VehicleComparators.byRate());

        boolean tieBrokenById = list.get(0).getId() == 5 && list.get(1).getId() == 10;

        assertTrue("Ordering: stable ID tie breaker on equal rates", tieBrokenById);
    }

    public static void testConcurrentRequests() {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService service = new RentalService(vehicleRepo, customerRepo);

        Vehicle car = new FuelCar(105, "Mercedes", "A200", 25000.0, "Available", 90.0, "AMG", 225.0, 8.0, "Auto", 5, 370, 5, 135.0);
        Customer c1 = new Customer(1, "Worker 1", "w1@test.com", "444");
        Customer c2 = new Customer(2, "Worker 2", "w2@test.com", "555");
        vehicleRepo.add(car);
        customerRepo.add(c1);
        customerRepo.add(c2);

        try {
            int[] successCounter = new int[1];
            Thread t1 = new Thread(() -> {
                try {
                    service.rentVehicle(1, 105, 1, new DailyPricingPolicy());
                    synchronized (successCounter) { successCounter[0]++; }
                } catch (Exception ignored) {}
            });
            Thread t2 = new Thread(() -> {
                try {
                    service.rentVehicle(2, 105, 1, new DailyPricingPolicy());
                    synchronized (successCounter) { successCounter[0]++; }
                } catch (Exception ignored) {}
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            boolean exactlyOne = successCounter[0] == 1;
            boolean vehicleRented = "Rented".equalsIgnoreCase(car.getStatus());
            boolean activeSetSize = service.getActiveRentedVehicleIds().size() == 1;

            assertTrue("Concurrent requests: exactly one success and consistent final state", exactlyOne && vehicleRented && activeSetSize);
        } catch (InterruptedException e) {
            assertTrue("Concurrent requests interrupted", false);
        }
    }
}
