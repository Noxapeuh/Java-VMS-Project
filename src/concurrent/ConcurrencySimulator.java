package concurrent;

import contract.PricingPolicy;
import service.RentalService;

public class ConcurrencySimulator {

    public static class SimulationResult {
        private final boolean worker1Success;
        private final boolean worker2Success;
        private final int successCount;
        private final int failureCount;

        public SimulationResult(boolean worker1Success, boolean worker2Success) {
            this.worker1Success = worker1Success;
            this.worker2Success = worker2Success;
            int successes = 0;
            int failures = 0;
            if (worker1Success) successes++; else failures++;
            if (worker2Success) successes++; else failures++;
            this.successCount = successes;
            this.failureCount = failures;
        }

        public boolean isWorker1Success() {
            return worker1Success;
        }

        public boolean isWorker2Success() {
            return worker2Success;
        }

        public int getSuccessCount() {
            return successCount;
        }

        public int getFailureCount() {
            return failureCount;
        }

        public boolean isExactlyOneSuccess() {
            return successCount == 1 && failureCount == 1;
        }
    }

    public static SimulationResult simulateConcurrentRent(RentalService service, int vehicleId, int customer1Id, int customer2Id, PricingPolicy policy) throws InterruptedException {
        RentalWorker w1 = new RentalWorker(service, customer1Id, vehicleId, 3, policy);
        RentalWorker w2 = new RentalWorker(service, customer2Id, vehicleId, 3, policy);

        Thread t1 = new Thread(w1, "Customer-1-Worker");
        Thread t2 = new Thread(w2, "Customer-2-Worker");

        t1.start();
        t2.start();

        t1.join(5000);
        t2.join(5000);

        return new SimulationResult(w1.isSuccess(), w2.isSuccess());
    }
}
