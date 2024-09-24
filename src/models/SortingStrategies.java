package models;

import java.util.List;

public interface SortingStrategies {
    void sort(List<Flight> flights, boolean ascending);
}
