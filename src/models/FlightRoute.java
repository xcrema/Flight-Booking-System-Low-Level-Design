package models;

import models.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightRoute {
    private Integer totalCost;
    private List<Flight> flightList;

    public FlightRoute(Integer totalCost, List<Flight> flightList) {
        this.totalCost = totalCost;
        this.flightList = flightList;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public void printRoute(SortingStrategies sorter, boolean ascending) {
        List<Flight> sortedFlights = new ArrayList<>(flightList);
        if (sorter != null) {
            sorter.sort(sortedFlights, ascending);
        }
        for (Flight flight : sortedFlights) {
            System.out.printf("%s to %s via %s for %s\n", flight.getSource(), flight.getDestination(), flight.getAirlineName(), flight.getCost());
        }
        System.out.println("Total Flights = " + flightList.size());
        System.out.println("Total cost = " + totalCost);
    }
}
