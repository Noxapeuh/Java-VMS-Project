package rental;

import model.Customer;
import model.Vehicle;

public class Rental {
    private final String rentalId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final int period;
    private final PriceBreakdown priceBreakdown;
    private String status;

    public static class PriceBreakdown {
        private final double baseRate;
        private final int duration;
        private final String policyName;
        private final double totalPrice;

        public PriceBreakdown(double baseRate, int duration, String policyName, double totalPrice) {
            this.baseRate = baseRate;
            this.duration = duration;
            this.policyName = policyName;
            this.totalPrice = totalPrice;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public int getDuration() {
            return duration;
        }

        public String getPolicyName() {
            return policyName;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        @Override
        public String toString() {
            return "PriceBreakdown{" +
                    "baseRate=" + baseRate +
                    ", duration=" + duration +
                    ", policyName='" + policyName + '\'' +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    public Rental() {
        this("RENT-" + System.currentTimeMillis(), new Customer(0, "Guest", "", ""), null, 1, new PriceBreakdown(0.0, 1, "Standard", 0.0), "Pending");
    }

    public Rental(String rentalId, Customer customer, Vehicle vehicle, int period, PriceBreakdown priceBreakdown, String status) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.period = period;
        this.priceBreakdown = priceBreakdown;
        this.status = status;
    }

    public Rental(String rentalId, Vehicle vehicle, String customerName, int period, String status) {
        this(rentalId, new Customer(0, customerName, "", ""), vehicle, period, new PriceBreakdown(vehicle != null ? vehicle.getRateData() : 0.0, period, "Standard", (vehicle != null ? vehicle.getRateData() : 0.0) * period), status);
    }

    public String getRentalId() {
        return rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerName() {
        return customer != null ? customer.getName() : "";
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getPeriod() {
        return period;
    }

    public PriceBreakdown getPriceBreakdown() {
        return priceBreakdown;
    }

    public String getQuote() {
        if (priceBreakdown != null) {
            return priceBreakdown.getTotalPrice() + "$";
        }
        return (vehicle != null ? vehicle.getRateData() * period : 0.0) + "$";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId='" + rentalId + '\'' +
                ", customer=" + getCustomerName() +
                ", vehicle=" + (vehicle != null ? vehicle.getModel() : "null") +
                ", period=" + period +
                ", quote='" + getQuote() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
