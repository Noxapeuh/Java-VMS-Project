package service;

import contract.PricingOption;
import contract.PricingPolicy;

@PricingOption("HOURLY")
public class HourlyPricingPolicy implements PricingPolicy {
    @Override
    public String getName() {
        return "Hourly";
    }

    @Override
    public double calculateQuote(double rateData, int period) {
        return (rateData / 24.0) * period * 1.15;
    }
}
