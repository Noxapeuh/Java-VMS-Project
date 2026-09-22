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

        public double getTotalPrice() {
            return totalPrice;
        }
    }

    public Rental(String rentalId, Customer customer, Vehicle vehicle, int period, PriceBreakdown priceBreakdown, String status) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.period = period;
        this.priceBreakdown = priceBreakdown;
        this.status = status;
    }

    public String getRentalId() {
        return rentalId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public PriceBreakdown getPriceBreakdown() {
        return priceBreakdown;
    }

    public String getQuote() {
        return priceBreakdown != null ? String.format(java.util.Locale.US, "%.2f$", priceBreakdown.getTotalPrice()) : "0.00$";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
