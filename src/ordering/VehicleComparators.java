package ordering;

import model.Vehicle;
import java.util.Comparator;

public class VehicleComparators {

    public static Comparator<Vehicle> byRate() {
        return (v1, v2) -> {
            int rateCompare = Double.compare(v1.getRateData(), v2.getRateData());
            if (rateCompare != 0) {
                return rateCompare;
            }
            return v1.compareTo(v2);
        };
    }

    public static Comparator<Vehicle> byMileage() {
        return (v1, v2) -> {
            int mileageCompare = Double.compare(v1.getMileage(), v2.getMileage());
            if (mileageCompare != 0) {
                return mileageCompare;
            }
            return v1.compareTo(v2);
        };
    }

    public static Comparator<Vehicle> byStatus() {
        return (v1, v2) -> {
            int statusCompare = v1.getStatus().compareToIgnoreCase(v2.getStatus());
            if (statusCompare != 0) {
                return statusCompare;
            }
            return v1.compareTo(v2);
        };
    }
}
