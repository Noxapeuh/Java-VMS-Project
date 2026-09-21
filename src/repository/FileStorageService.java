package repository;

import model.Customer;
import model.FuelCar;
import model.Motorbike;
import model.Truck;
import model.Vehicle;
import rental.Rental;
import rental.Rental.PriceBreakdown;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {

    public static List<Vehicle> loadVehicles(String filePath) {
        List<Vehicle> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return list;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 7) {
                    continue;
                }
                String type = parts[0].trim();
                int id = Integer.parseInt(parts[1].trim());
                String brand = parts[2].trim();
                String model = parts[3].trim();
                double mileage = Double.parseDouble(parts[4].trim());
                String status = parts[5].trim();
                double rateData = Double.parseDouble(parts[6].trim());

                if ("CAR".equalsIgnoreCase(type)) {
                    list.add(new FuelCar(id, brand, model, mileage, status, rateData, "Standard", 180.0, 9.5, "Manual", 5, 450, 5, 120.0));
                } else if ("TRUCK".equalsIgnoreCase(type)) {
                    list.add(new Truck(id, model, mileage, status, rateData, "Diesel", 450, 2, 3.8, 7500.0, 18000, true, brand));
                } else if ("MOTORBIKE".equalsIgnoreCase(type)) {
                    list.add(new Motorbike(id, model, mileage, status, rateData, "Gasoline", 14, 75, 2, "Road", 2, "A2", 805.0, false, brand));
                }
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return list;
    }

    public static List<Customer> loadCustomers(String filePath) {
        List<Customer> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return list;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String phone = parts[3].trim();
                    list.add(new Customer(id, name, email, phone));
                }
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return list;
    }

    public static List<Rental> loadRentals(String filePath, Repository<Vehicle> vehicleRepo, Repository<Customer> customerRepo) {
        List<Rental> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return list;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String rentalId = parts[0].trim();
                    int customerId = Integer.parseInt(parts[1].trim());
                    int vehicleId = Integer.parseInt(parts[2].trim());
                    int period = Integer.parseInt(parts[3].trim());
                    String policyName = parts[4].trim();
                    double total = Double.parseDouble(parts[5].trim());
                    String status = parts[6].trim();

                    Customer customer = customerRepo.getById(customerId);
                    Vehicle vehicle = vehicleRepo.getById(vehicleId);
                    if (customer != null && vehicle != null) {
                        PriceBreakdown breakdown = new PriceBreakdown(vehicle.getRateData(), period, policyName, total);
                        list.add(new Rental(rentalId, customer, vehicle, period, breakdown, status));
                    }
                }
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return list;
    }

    public static void saveVehicles(String filePath, List<Vehicle> vehicles) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Vehicle v : vehicles) {
                String type = "CAR";
                if (v instanceof Truck) {
                    type = "TRUCK";
                } else if (v instanceof Motorbike) {
                    type = "MOTORBIKE";
                }
                writer.println(type + "," + v.getId() + "," + v.getBrand() + "," + v.getModel() + "," + v.getMileage() + "," + v.getStatus() + "," + v.getRateData());
            }
        } catch (IOException ignored) {
        }
    }

    public static void saveRentals(String filePath, List<Rental> rentals) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Rental r : rentals) {
                writer.println(r.getRentalId() + "," + r.getCustomer().getId() + "," + r.getVehicle().getId() + "," + r.getPeriod() + "," + r.getPriceBreakdown().getPolicyName() + "," + r.getPriceBreakdown().getTotalPrice() + "," + r.getStatus());
            }
        } catch (IOException ignored) {
        }
    }
}
