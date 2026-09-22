package app;

import contract.PricingOption;
import contract.PricingPolicy;
import model.*;
import ordering.VehicleComparators;
import rental.Fleet;
import rental.Rental;
import repository.GenericRepository;
import service.*;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        GenericRepository<Vehicle> vehicleRepo = new GenericRepository<>();
        GenericRepository<Customer> customerRepo = new GenericRepository<>();
        vehicleRepo.addAll(GenericRepository.loadVehicles("data/vehicles.txt"));
        customerRepo.addAll(GenericRepository.loadCustomers("data/customers.txt"));

        RentalService rentalService = new RentalService(vehicleRepo, customerRepo);
        Fleet fleet = new Fleet();
        fleet.addAll(vehicleRepo.getAll());

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU VRMS ---");
            System.out.println("1. Display fleet");
            System.out.println("2. Sort vehicles");
            System.out.println("3. Rent a vehicle");
            System.out.println("4. Return a vehicle");
            System.out.println("5. Fleet stats (Streams)");
            System.out.println("6. Pricing policies (Reflection)");
            System.out.println("7. Concurrency test (Threads)");
            System.out.println("8. Save and Exit");
            System.out.print("Choice: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\n--- Fleet ---");
                for (Vehicle v : vehicleRepo.getAll()) {
                    System.out.println(v.getId() + ". " + v.getBrand() + " " + v.getModel() + " - " + v.getStatus() + " (" + v.getRateData() + "$/day)");
                }

            } else if (choice.equals("2")) {
                System.out.print("Sort by (1.Rate, 2.Mileage, 3.Status): ");
                String sortChoice = sc.nextLine().trim();
                List<Vehicle> list = vehicleRepo.getAll();
                if (sortChoice.equals("1")) Collections.sort(list, VehicleComparators.byRate());
                else if (sortChoice.equals("2")) Collections.sort(list, VehicleComparators.byMileage());
                else if (sortChoice.equals("3")) Collections.sort(list, VehicleComparators.byStatus());

                for (Vehicle v : list) {
                    System.out.println(v.getId() + ". " + v.getBrand() + " " + v.getModel() + " - " + v.getRateData() + "$ - " + v.getMileage() + "km");
                }

            } else if (choice.equals("3")) {
                try {
                    System.out.print("Customer ID: ");
                    int cId = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Vehicle ID: ");
                    int vId = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Policy (1.Daily, 2.Hourly, 3.Weekly): ");
                    String p = sc.nextLine().trim();

                    PricingPolicy policy;
                    String unit;
                    if (p.equals("2")) {
                        policy = new HourlyPricingPolicy();
                        unit = "hours";
                    } else if (p.equals("3")) {
                        policy = new WeeklyPricingPolicy();
                        unit = "weeks";
                    } else {
                        policy = new DailyPricingPolicy();
                        unit = "days";
                    }

                    System.out.print("Duration in " + unit + ": ");
                    int duration = Integer.parseInt(sc.nextLine().trim());

                    Rental r = rentalService.rentVehicle(cId, vId, duration, policy);
                    System.out.println("Rental confirmed! ID: " + r.getRentalId() + " | Total: " + r.getQuote());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (choice.equals("4")) {
                System.out.print("Enter Rental ID to return: ");
                String rId = sc.nextLine().trim();
                try {
                    rentalService.returnVehicle(rId);
                    System.out.println("Vehicle returned successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (choice.equals("5")) {
                long available = fleet.getVehicles().stream()
                        .filter(v -> v.getStatus().equalsIgnoreCase("Available"))
                        .count();
                double totalMoney = rentalService.getRentalHistory().stream()
                        .mapToDouble(r -> r.getPriceBreakdown().getTotalPrice())
                        .sum();
                System.out.println("Available vehicles: " + available + "/" + fleet.size());
                System.out.println("Total revenue: " + totalMoney + "$");

            } else if (choice.equals("6")) {
                System.out.println("\n--- Policies found by reflection ---");
                Class<?>[] list = { HourlyPricingPolicy.class, DailyPricingPolicy.class, WeeklyPricingPolicy.class };
                for (Class<?> c : list) {
                    PricingOption opt = c.getAnnotation(PricingOption.class);
                    if (opt != null) {
                        System.out.println(c.getSimpleName() + " -> " + opt.value());
                    }
                }

            } else if (choice.equals("7")) {
                System.out.println("\n--- Testing 2 threads on vehicle 999 ---");
                GenericRepository<Vehicle> simV = new GenericRepository<>();
                GenericRepository<Customer> simC = new GenericRepository<>();
                RentalService simService = new RentalService(simV, simC);

                simV.add(new FuelCar(999, "Toyota", "TestCorolla", 1000, "Available", 50, "Std", 150, 10, "Manual", 5, 200, 5, 100));
                simC.add(new Customer(1, "Client 1", "c1@test.com", "111"));
                simC.add(new Customer(2, "Client 2", "c2@test.com", "222"));

                Thread t1 = new Thread(() -> {
                    try {
                        simService.rentVehicle(1, 999, 1, new DailyPricingPolicy());
                        System.out.println("Thread 1: Success!");
                    } catch (Exception e) {
                        System.out.println("Thread 1: Failed (" + e.getMessage() + ")");
                    }
                });

                Thread t2 = new Thread(() -> {
                    try {
                        simService.rentVehicle(2, 999, 1, new DailyPricingPolicy());
                        System.out.println("Thread 2: Success!");
                    } catch (Exception e) {
                        System.out.println("Thread 2: Failed (" + e.getMessage() + ")");
                    }
                });

                try {
                    t1.start();
                    t2.start();
                    t1.join();
                    t2.join();
                } catch (Exception ignored) {}

            } else if (choice.equals("8")) {
                GenericRepository.saveVehicles("data/vehicles.txt", vehicleRepo.getAll());
                System.out.println("Saved data to data/vehicles.txt. Bye!");
                break;

            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
}
