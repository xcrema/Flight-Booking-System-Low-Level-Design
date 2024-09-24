package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Airline {

    private final String airlineNo;

    private final String airlineName;

    private final List<Flight> flights;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
        airlineNo = UUID.randomUUID().toString();
        flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public String getAirlineName() {
        return airlineName;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public String getAirlineNo() {
        return airlineNo;
    }
}
