package rental;

import model.Vehicle;

public class Rental {
    private Vehicle vehicle;
    private String customerName;
    private int period;
    private String quote;
    private String status;

    public Rental(Vehicle vehicle, String customerName, int period, String status) {
        this.vehicle = vehicle;
        this.customerName = customerName;
        this.period = period;
        this.quote =  (vehicle.getRateData() * period) + "$";
        this.status = status;
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

