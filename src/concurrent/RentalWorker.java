package concurrent;

import contract.PricingPolicy;
import exception.RentalException;
import rental.Rental;
import service.RentalService;

public class RentalWorker implements Runnable {
    private final RentalService rentalService;
    private final int customerId;
    private final int vehicleId;
    private final int period;
    private final PricingPolicy policy;

    private boolean success = false;
    private Exception failureReason = null;
    private Rental rentalResult = null;

    public RentalWorker(RentalService rentalService, int customerId, int vehicleId, int period, PricingPolicy policy) {
        this.rentalService = rentalService;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.period = period;
        this.policy = policy;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        try {
            rentalResult = rentalService.rentVehicle(customerId, vehicleId, period, policy);
            success = true;
        } catch (RentalException | IllegalArgumentException e) {
            failureReason = e;
            success = false;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getFailureReason() {
        return failureReason;
    }

    public Rental getRentalResult() {
        return rentalResult;
    }
}
