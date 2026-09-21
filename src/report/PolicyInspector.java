package report;

import contract.PricingOption;
import java.util.ArrayList;
import java.util.List;

public class PolicyInspector {

    public static List<String> inspectPolicies(Class<?>... classes) {
        List<String> results = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(PricingOption.class)) {
                PricingOption option = clazz.getAnnotation(PricingOption.class);
                results.add(option.value() + " : " + clazz.getSimpleName());
            }
        }
        return results;
    }
}
