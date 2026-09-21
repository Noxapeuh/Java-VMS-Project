package service;

import contract.PricingOption;
import contract.PricingPolicy;

@PricingOption("DAILY")
public class DailyPricingPolicy implements PricingPolicy {
    @Override
    public String getName() {
        return "Daily";
    }

    @Override
    public double calculateQuote(double rateData, int period) {
        return rateData * period;
    }
}
