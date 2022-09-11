package co.develhope.customqueries2.entities;

import java.util.Random;

public enum FlightStatus {
    ONTIME,
    DELAYED,
    CANCELLED;

    //The status is generated randomly
    private static final Random PRNG = new Random();

    public static FlightStatus randomFlightStatus() {
        FlightStatus[] flightStatuses = values();
        return flightStatuses[PRNG.nextInt(flightStatuses.length)];
    }
}
