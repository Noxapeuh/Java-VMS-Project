package rental;
import java.util.Scanner;
import model.Vehicle;

public class Rental {
    private String rentalId;
    private Vehicle vehicle;
    private String customerName;
    private int period;
    private String quote;
    private String status;

    public Rental(String rentalId, Vehicle vehicle, String customerName, int period, String status) {
        this.rentalId = rentalId;
        this.vehicle = vehicle;
        this.customerName = customerName;
        this.period = period;
        this.quote =  (vehicle.getRateData() * period) + "$";
        this.status = status;
    }

    public Rental(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        this.customerName = sc.nextLine();
        System.out.print("Enter rental period (in days): ");
        this.period = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter vehicle ID: ");
        int vehicleId = sc.nextInt();
        sc.nextLine();
        // Use singleton repository (populated by main app)
        repository.VehicleRepository repo = repository.VehicleRepositoryImpl.getInstance();
        this.vehicle = repo.getVehicleById(vehicleId);
        if (this.vehicle == null) {
            throw new IllegalArgumentException("No vehicle found with id: " + vehicleId);
        }
        // compute quote after vehicle is assigned
        this.quote = (vehicle.getRateData() * period) + "$";
        this.status = "Pending";
        this.rentalId = "R" + System.currentTimeMillis();
        System.out.print("The price for the rental is: " + this.quote);
        System.out.print("Rental created successfully for " + customerName + " for a period of " + period + " days. The quote is: " + quote + " with vehicle " + vehicleId);
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getPeriod() {
        return period;
    }

    public String getQuote() {
        return quote;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return "\nRental{" +
                "vehicle=" + vehicle.toString() +
                ", customerName='" + customerName + '\'' +
                ", period=" + period +
                ", quote='" + quote + '\'' +
                ", status='" + status + '\'' +
                "}\n";
    }
}

