package app;
import model.*;


public class MainApp {
    public static void main(String[] args) {
        Truck truck = new Truck(1, "Volvo FH", 120000, "Available", 150.0, "Diesel", 500, 2, 4.0, 8000.0, 20000, true);
        System.out.println(truck.toString());
        System.out.println("Is the truck rentable? " + truck.isRentable());
    }
}
