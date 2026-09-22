package repository;

import contract.Identifiable;
import model.Customer;
import model.FuelCar;
import model.Motorbike;
import model.Truck;
import model.Vehicle;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenericRepository<T extends Identifiable> {
    private final List<T> storage = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            storage.add(item);
        }
    }

    public T getById(int id) {
        for (T item : storage) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<T> getAll() {
        return new ArrayList<>(storage);
    }

    public void addAll(List<T> items) {
        if (items != null) {
            storage.addAll(items);
        }
    }

    public boolean removeById(int id) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                storage.remove(i);
                return true;
            }
        }
        return false;
    }

    public int size() {
        return storage.size();
    }

    public static List<Vehicle> loadVehicles(String path) {
        List<Vehicle> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 7) {
                    int id = Integer.parseInt(p[1].trim());
                    String brand = p[2].trim();
                    String model = p[3].trim();
                    double km = Double.parseDouble(p[4].trim());
                    String status = p[5].trim();
                    double rate = Double.parseDouble(p[6].trim());

                    if (p[0].equalsIgnoreCase("TRUCK")) {
                        list.add(new Truck(id, model, km, status, rate, "Diesel", 450, 2, 3.8, 7500.0, 18000, true, brand));
                    } else if (p[0].equalsIgnoreCase("MOTORBIKE")) {
                        list.add(new Motorbike(id, model, km, status, rate, "Gasoline", 14, 75, 2, "Road", 2, "A2", 805.0, false, brand));
                    } else {
                        list.add(new FuelCar(id, brand, model, km, status, rate, "Standard", 180.0, 9.5, "Manual", 5, 450, 5, 120.0));
                    }
                }
            }
        } catch (Exception ignored) {}
        return list;
    }

    public static List<Customer> loadCustomers(String path) {
        List<Customer> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 4) {
                    list.add(new Customer(Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(), p[3].trim()));
                }
            }
        } catch (Exception ignored) {}
        return list;
    }

    public static void saveVehicles(String path, List<Vehicle> list) {
        try (PrintWriter pw = new PrintWriter(new File(path))) {
            for (Vehicle v : list) {
                String type = v instanceof Truck ? "TRUCK" : (v instanceof Motorbike ? "MOTORBIKE" : "CAR");
                pw.println(type + "," + v.getId() + "," + v.getBrand() + "," + v.getModel() + "," + v.getMileage() + "," + v.getStatus() + "," + v.getRateData());
            }
        } catch (Exception ignored) {}
    }
}
