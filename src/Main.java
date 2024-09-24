import models.SortingStrategies;

public class Main {
    public static void main(String[] args) {
        FlightBookingSystem flightBookingSystem = new FlightBookingSystem();

        Thread thread1 = new Thread(() -> flightBookingSystem.registerFlight("JetAir", "DEL", "BLR", 500, false, System.currentTimeMillis()));
        Thread thread2 = new Thread(() -> flightBookingSystem.registerFlight("JetAir", "BLR", "LON", 1000, false, System.currentTimeMillis()));
        Thread thread3 = new Thread(() -> flightBookingSystem.registerFlight("Delta", "DEL", "LON", 2000, false, System.currentTimeMillis()));
        Thread thread4 = new Thread(() -> flightBookingSystem.registerFlight("Delta", "LON", "NYC", 2000, false, System.currentTimeMillis()));
        Thread thread5 = new Thread(() -> flightBookingSystem.registerFlight("IndiGo", "LON", "NYC", 2500, true, System.currentTimeMillis()));
        Thread thread6 = new Thread(() -> flightBookingSystem.registerFlight("IndiGo", "DEL", "BLR", 600, true, System.currentTimeMillis()));
        Thread thread7 = new Thread(() -> flightBookingSystem.registerFlight("IndiGo", "BLR", "PAR", 800, true, System.currentTimeMillis()));
        Thread thread8 = new Thread(() -> flightBookingSystem.registerFlight("IndiGo", "PAR", "LON", 300, true, System.currentTimeMillis()));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
        } catch (InterruptedException e) {
            //log the error
        }
        System.out.println("\n\n\n\n\n\n\n\n\n");

        flightBookingSystem.searchFlight("DEL", "NYC", false, "DEPARTURE", true);

        flightBookingSystem.searchFlight("DEL", "NYC", true, "DEPARTURE", true);
    }
}