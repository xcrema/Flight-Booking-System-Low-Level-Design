package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DepartureTimeSortingStrategy implements SortingStrategies {
    @Override
    public void sort(List<Flight> flights, boolean ascending) {
        flights.sort(Comparator.comparing(Flight::getDepartureTime));
        if (!ascending) {
            Collections.reverse(flights);
        }
    }
}
