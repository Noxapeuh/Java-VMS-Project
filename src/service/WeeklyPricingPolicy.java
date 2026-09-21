package service;

import contract.PricingOption;
import contract.PricingPolicy;

@PricingOption("WEEKLY")
public class WeeklyPricingPolicy implements PricingPolicy {
    @Override
    public String getName() {
        return "Weekly";
    }

    @Override
    public double calculateQuote(double rateData, int period) {
        return (rateData * period * 7.0) * 0.85;
    }
}
