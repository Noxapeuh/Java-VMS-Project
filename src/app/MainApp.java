package app;

import contract.PricingOption;
import contract.PricingPolicy;
import exception.RentalException;
import model.*;
import ordering.VehicleComparators;
import rental.Fleet;
import rental.Rental;
import repository.FileStorageService;
import repository.GenericRepository;
import repository.Repository;
import service.*;
import test.SystemTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static final String VEHICLES_FILE = "data/vehicles.txt";
    private static final String CUSTOMERS_FILE = "data/customers.txt";
    private static final String RENTALS_FILE = "data/rentals.txt";

    public static void main(String[] args) {
        Repository<Vehicle> vehicleRepo = new GenericRepository<>();
        Repository<Customer> customerRepo = new GenericRepository<>();
        RentalService rentalService = new RentalService(vehicleRepo, customerRepo);
        Fleet fleet = new Fleet();

        new File("data").mkdirs();

        List<Vehicle> loadedVehicles = FileStorageService.loadVehicles(VEHICLES_FILE);
        if (loadedVehicles.isEmpty()) {
            Truck defaultTruck = new Truck(1, "Volvo FH", 120000, "Available", 150.0, "Diesel", 500, 2, 4.0, 8000.0, 20000, true, "Volvo");
            FuelCar defaultCar = new FuelCar(2, "Toyota", "Corolla", 45000.0, "Available", 60.0, "Standard", 180.0, 9.5, "Manual", 5, 450, 5, 120.0);
            Motorbike defaultBike = new Motorbike(3, "Harley-Davidson Street 750", 8000, "Available", 100.0, "Petrol", 13, 53, 2, "Cruiser", 2, "A", 735.0, true, "Harley-Davidson");
            ElectricCar defaultElectric = new ElectricCar(4, "Tesla", "Model 3", 25000.0, "Available", 90.0, "Long Range", 233.0, 4.4, "Auto", 5, 425, 5, 0.0);
            HybridCar defaultHybrid = new HybridCar(5, "Toyota", "Prius", 60000.0, "Available", 70.0, "Hybrid", 170.0, 10.5, "Auto", 5, 400, 5, 75.0);

            vehicleRepo.add(defaultTruck);
            vehicleRepo.add(defaultCar);
            vehicleRepo.add(defaultBike);
            vehicleRepo.add(defaultElectric);
            vehicleRepo.add(defaultHybrid);
        } else {
            vehicleRepo.addAll(loadedVehicles);
        }

        List<Customer> loadedCustomers = FileStorageService.loadCustomers(CUSTOMERS_FILE);
        if (loadedCustomers.isEmpty()) {
            customerRepo.add(new Customer(1, "Alice Smith", "alice@example.com", "555-0101"));
            customerRepo.add(new Customer(2, "Bob Jones", "bob@example.com", "555-0102"));
            customerRepo.add(new Customer(3, "Charlie Brown", "charlie@example.com", "555-0103"));
        } else {
            customerRepo.addAll(loadedCustomers);
        }

        fleet.addAll(vehicleRepo.getAll());

        List<Rental> loadedRentals = FileStorageService.loadRentals(RENTALS_FILE, vehicleRepo, customerRepo);
        for (Rental r : loadedRentals) {
            rentalService.getRentalHistory();
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== VEHICLE RENTAL MANAGEMENT SYSTEM (VRMS) =====");
            System.out.println("1. Display vehicle fleet");
            System.out.println("2. Sort vehicles (Comparators)");
            System.out.println("3. Rent a vehicle");
            System.out.println("4. Return a vehicle");
            System.out.println("5. Generate analytical reports (Streams & Iterators)");
            System.out.println("6. Inspect pricing policies (Reflection & Annotations)");
            System.out.println("7. Simulate concurrent requests (Threads & Locks)");
            System.out.println("8. Run system test harness");
            System.out.println("9. Save and Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            if ("1".equals(input)) {
                System.out.println("\n--- Current Fleet ---");
                for (Vehicle v : vehicleRepo.getAll()) {
                    System.out.println("[" + v.getId() + "] " + v.getBrand() + " " + v.getModel() + " | Status: " + v.getStatus() + " | Rate: " + v.getRateData() + "$/day | Mileage: " + v.getMileage() + " km");
                }
            } else if ("2".equals(input)) {
                System.out.println("\nSort by: 1. Rate (ascending) | 2. Mileage | 3. Status");
                System.out.print("Choice: ");
                String sortChoice = scanner.nextLine().trim();
                List<Vehicle> sorted = new ArrayList<>(vehicleRepo.getAll());
                if ("1".equals(sortChoice)) {
                    Collections.sort(sorted, VehicleComparators.byRate());
                } else if ("2".equals(sortChoice)) {
                    Collections.sort(sorted, VehicleComparators.byMileage());
                } else if ("3".equals(sortChoice)) {
                    Collections.sort(sorted, VehicleComparators.byStatus());
                } else {
                    Collections.sort(sorted);
                }
                for (Vehicle v : sorted) {
                    System.out.println("[" + v.getId() + "] " + v.getBrand() + " " + v.getModel() + " | Rate: " + v.getRateData() + "$ | Mileage: " + v.getMileage() + " km | Status: " + v.getStatus());
                }
            } else if ("3".equals(input)) {
                try {
                    System.out.print("Customer ID: ");
                    int cId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Vehicle ID: ");
                    int vId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Duration (days/units): ");
                    int duration = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("Policy: 1. Daily | 2. Hourly | 3. Weekly");
                    System.out.print("Choice: ");
                    String pChoice = scanner.nextLine().trim();
                    PricingPolicy policy = new DailyPricingPolicy();
                    if ("2".equals(pChoice)) policy = new HourlyPricingPolicy();
                    if ("3".equals(pChoice)) policy = new WeeklyPricingPolicy();

                    Rental rental = rentalService.rentVehicle(cId, vId, duration, policy);
                    System.out.println("Success: Rental created with ID=" + rental.getRentalId() + ", Total=" + rental.getQuote());
                } catch (RentalException | NumberFormatException e) {
                    System.out.println("Rental error: " + e.getMessage());
                }
            } else if ("4".equals(input)) {
                System.out.print("Rental ID to return: ");
                String rId = scanner.nextLine().trim();
                try {
                    rentalService.returnVehicle(rId);
                    System.out.println("Vehicle returned successfully.");
                } catch (RentalException e) {
                    System.out.println("Return error: " + e.getMessage());
                }
            } else if ("5".equals(input)) {
                System.out.println("\n--- Analytical Reports ---");
                long availableCount = fleet.getVehicles().stream()
                        .filter(v -> "Available".equalsIgnoreCase(v.getStatus()))
                        .count();
                System.out.println("Available vehicles (Stream): " + availableCount);

                long dueCount = 0;
                java.util.Iterator<Vehicle> it = fleet.iterator();
                while (it.hasNext()) {
                    Vehicle v = it.next();
                    if (v.getMileage() >= 80000.0) {
                        dueCount++;
                    }
                }
                System.out.println("Vehicles over 80,000 km (Iterator): " + dueCount);

                double revenue = rentalService.getRentalHistory().stream()
                        .mapToDouble(r -> r.getPriceBreakdown().getTotalPrice())
                        .sum();
                System.out.println("Total revenue collected: " + revenue + "$");

                double utilization = fleet.size() > 0 ? ((double) rentalService.getActiveRentedVehicleIds().size() / fleet.size()) * 100.0 : 0.0;
                System.out.println("Fleet utilization rate: " + String.format("%.2f", utilization) + "%");
            } else if ("6".equals(input)) {
                System.out.println("\n--- Pricing Policies Discovered via Reflection ---");
                Class<?>[] policies = { HourlyPricingPolicy.class, DailyPricingPolicy.class, WeeklyPricingPolicy.class };
                for (Class<?> p : policies) {
                    if (p.isAnnotationPresent(PricingOption.class)) {
                        PricingOption opt = p.getAnnotation(PricingOption.class);
                        System.out.println("Available option: " + opt.value() + " : " + p.getSimpleName());
                    }
                }
            } else if ("7".equals(input)) {
                System.out.println("\n--- Concurrent Rental Request Simulation (Module 9) ---");
                try {
                    Repository<Vehicle> simVehicleRepo = new GenericRepository<>();
                    Repository<Customer> simCustomerRepo = new GenericRepository<>();
                    RentalService simService = new RentalService(simVehicleRepo, simCustomerRepo);

                    Vehicle testCar = new FuelCar(999, "Toyota", "Corolla", 10000.0, "Available", 60.0, "Simulation", 180.0, 9.0, "Manual", 5, 300, 5, 100.0);
                    simVehicleRepo.add(testCar);
                    simCustomerRepo.add(new Customer(1, "Customer 1", "c1@test.com", "111"));
                    simCustomerRepo.add(new Customer(2, "Customer 2", "c2@test.com", "222"));

                    int[] successCounter = new int[1];
                    Thread t1 = new Thread(() -> {
                        try {
                            simService.rentVehicle(1, 999, 1, new DailyPricingPolicy());
                            synchronized (successCounter) { successCounter[0]++; }
                        } catch (Exception ignored) {}
                    });
                    Thread t2 = new Thread(() -> {
                        try {
                            simService.rentVehicle(2, 999, 1, new DailyPricingPolicy());
                            synchronized (successCounter) { successCounter[0]++; }
                        } catch (Exception ignored) {}
                    });

                    t1.start();
                    t2.start();
                    t1.join();
                    t2.join();

                    int successes = successCounter[0];
                    int failures = 2 - successes;
                    System.out.println("Simulation result: Successes=" + successes + ", Failures=" + failures + ", Exactly one success=" + (successes == 1));
                } catch (InterruptedException e) {
                    System.out.println("Simulation interrupted: " + e.getMessage());
                }
            } else if ("8".equals(input)) {
                System.out.println("\n--- Running Automated System Integration Tests ---");
                SystemTest.main(new String[0]);
            } else if ("9".equals(input)) {
                FileStorageService.saveVehicles(VEHICLES_FILE, vehicleRepo.getAll());
                FileStorageService.saveRentals(RENTALS_FILE, rentalService.getRentalHistory());
                System.out.println("Data saved successfully. Application terminated.");
                running = false;
            } else {
                System.out.println("Invalid choice. Please enter a valid number between 1 and 9.");
            }
        }
    }
}
