package models;

import java.util.UUID;

public class Flight {

    private final String flightNo;

    private final String airlineName;

    private final String source;

    private final String destination;

    private final Integer cost;

    private final Boolean mealIncluded;

    private final long departureTime;

    public Flight(String airlineName, String source, String destination, Integer cost, Boolean mealIncluded, long departureTime) {
        this.flightNo = UUID.randomUUID().toString();
        this.airlineName = airlineName;
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.mealIncluded = mealIncluded;
        this.departureTime = departureTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getCost() {
        return cost;
    }

    public Boolean getMealIncluded() {
        return mealIncluded;
    }

    public long getDepartureTime() {
        return departureTime;
    }
}
