package app;
import model.*;
import rental.*;


public class MainApp {
    public static void main(String[] args) {
        Truck truck = new Truck(1, "Volvo FH", 120000, "Available", 150.0, "Diesel", 500, 2, 4.0, 8000.0, 20000, true, "Volvo");
        System.out.println(truck.toString());
        Jet jet = new Jet(2, "HondaJet Elite", 500, "Available", 12000, "Light Jet", "GE Honda HF120 turbofan", 6, 2018, 5000, 782, 2, 0, true, false, "CDG", "Honda");
        System.out.println(jet);

        Motorbike motorbike = new Motorbike(3, "Harley-Davidson Street 750", 8000, "Available", 100.0, "Petrol", 13, 53, 2, "Cruiser", 2, "A", 735.0, true, "Harley-Davidson");
        System.out.println(motorbike);

        // register vehicles in repository so Rental can look them up
        repository.VehicleRepositoryImpl repo = repository.VehicleRepositoryImpl.getInstance();
        repo.addVehicle(truck);
        repo.addVehicle(jet);
        repo.addVehicle(motorbike);

        Rental rent = new Rental();
        System.out.println(rent);
    }
}
