import models.DepartureTimeSortingStrategy;
import models.SortingStrategies;

public class SortingStrategyFactoryBuilder {

    public static SortingStrategies getSortingStrategy(String strategyType) {
        if (strategyType.equals("DEPARTURE")) {
            new DepartureTimeSortingStrategy();
        }

        return null;
    }
}
