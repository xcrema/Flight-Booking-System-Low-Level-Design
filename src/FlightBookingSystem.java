import models.Airline;
import models.Flight;
import models.FlightRoute;
import models.SortingStrategies;
import utils.Utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FlightBookingSystem {

    private final Map<String, Airline> airlines;
    private final Map<String, List<Flight>> flightsFromCity;
    private final ReadWriteLock lock;

    public FlightBookingSystem() {
        airlines = new ConcurrentHashMap<>();
        flightsFromCity = new ConcurrentHashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    public void registerFlight(String airlineName, String source, String destination, Integer cost, Boolean mealsIncluded, long departureTime) {
        if (Utils.checkForEmptyOfNull(airlineName) || Utils.checkForEmptyOfNull(airlineName) ||
        Utils.checkForEmptyOfNull(airlineName) || Utils.checkForEmptyOfNull(airlineName)|| Utils.checkForEmptyOfNull(airlineName)) {
            System.out.println("Details cannot be null.");
            return;
        }

        if (source.equals(destination)) {
            System.out.println("Source and Destination cannot be same for airline: " + airlineName);
            return;
        }

        if (cost <= 0) {
            System.out.println("Cost of the flight cannot be less than or equal to 0 for airline: " + airlineName);
            return;
        }

        lock.writeLock().lock();
        try {
            Airline airline = airlines.computeIfAbsent(airlineName, flight -> new Airline(airlineName));
            Flight flight = new Flight(airlineName, source, destination, cost, mealsIncluded, departureTime);
            airline.addFlight(flight);
            flightsFromCity.computeIfAbsent(source, data -> new CopyOnWriteArrayList<>()).add(flight);
            System.out.printf("Flight added for %s from %s to %s%n", airlineName, source, destination);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void searchFlight(String source, String destination, boolean mealRequired, String sortingStrategy, boolean ascending) {
        lock.readLock().lock();
        try {
            FlightRoute minHopRoute = findRouteWithMinHops(source, destination, mealRequired);
            FlightRoute cheapestRoute = findCheapestRoute(source, destination, mealRequired);

            SortingStrategies sorter = SortingStrategyFactoryBuilder.getSortingStrategy(sortingStrategy);

            if (minHopRoute != null) {
                System.out.println("Route with Minimum Hops: ");
                minHopRoute.printRoute(sorter, ascending);
            } else {
                System.out.println("No route found with minimum hops.");
            }

            if (cheapestRoute != null) {
                System.out.println("Cheapest Route: ");
                cheapestRoute.printRoute(sorter, ascending);
            } else {
                System.out.println("No cheapest flight route found.");
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    private FlightRoute findRouteWithMinHops(String source, String destination, boolean mealRequired) {
        Queue<List<Flight>> queue = new LinkedList<>();
        Map<String, Integer> visited = new HashMap<>();

        queue.add(new ArrayList<>());
        visited.put(source, 0);

        while (!queue.isEmpty()) {
            List<Flight> currentRoute = queue.poll();
            String currentCity = currentRoute.isEmpty() ?
                    source :
                    currentRoute.getLast().getDestination();

            if (currentCity.equals(destination)) {
                int totalCost = currentRoute.stream().mapToInt(Flight::getCost).sum();
                return new FlightRoute(totalCost, currentRoute);
            }

            for (Flight flight : flightsFromCity.getOrDefault(currentCity, new ArrayList<>())) {
                if ((mealRequired && !flight.getMealIncluded()) || visited.getOrDefault(flight.getDestination(), Integer.MAX_VALUE) <= currentRoute.size() + 1) {
                    continue;
                }
                visited.put(flight.getDestination(), currentRoute.size()+1);
                List<Flight> newRoute = new ArrayList<>(currentRoute);
                newRoute.add(flight);
                queue.add(newRoute);
            }
        }

        return null;
    }

    private FlightRoute findCheapestRoute(String source, String destination, boolean mealRequired) {
        PriorityQueue<FlightRoute> queue = new PriorityQueue<>(Comparator.comparingInt(FlightRoute::getTotalCost));
        queue.add(new FlightRoute(0, new ArrayList<>()));

        Map<String, Integer> visited = new HashMap<>();
        visited.put(source, 0);

        while (!queue.isEmpty()) {
            FlightRoute currentRoute = queue.poll();
            String currentCity = currentRoute.getFlightList().isEmpty() ? source : currentRoute.getFlightList().getLast().getDestination();

            if (currentCity.equals(destination)) {
                return currentRoute;
            }

            for (Flight flight : flightsFromCity.getOrDefault(currentCity, Collections.emptyList())) {
                if (mealRequired && !flight.getMealIncluded()) {
                    continue;
                }

                int newCost = currentRoute.getTotalCost() + flight.getCost();
                if (newCost < visited.getOrDefault(flight.getDestination(), Integer.MAX_VALUE)) {
                    visited.put(flight.getDestination(), newCost);
                    List<Flight> newRouteFlights = new ArrayList<>(currentRoute.getFlightList());
                    newRouteFlights.add(flight);
                    queue.add(new FlightRoute(newCost, newRouteFlights));
                }
            }
        }

        return null;
    }

}
