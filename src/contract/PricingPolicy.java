package contract;

public interface PricingPolicy {
    String getName();
    double calculateQuote(double rateData, int period);
}
