package co.develhope.customqueries2.controllers;

import co.develhope.customqueries2.entities.Flight;
import co.develhope.customqueries2.entities.FlightStatus;
import co.develhope.customqueries2.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
// Mapped flights
@RequestMapping("/flights")
public class FlightController {

    // All the string values are randomly generated
    public String generateRandomValueForFlight() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @Autowired
    private FlightRepository flightRepository;


    // Provisioning of n flights (where n is an optional query param; if absent, n=100)
    @GetMapping("/provisioning")
    public void provisionFlights(@RequestParam(required = false) Integer n){
        if(n == null) n=100;
        List<Flight> newFlights = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Flight flight = new Flight();
            flight.setDescription(generateRandomValueForFlight());
            flight.setFromAirport(generateRandomValueForFlight());
            flight.setToAirport(generateRandomValueForFlight());
            flight.setFlightStatus(FlightStatus.randomFlightStatus());
            newFlights.add(flight);
        }
        flightRepository.saveAll(newFlights);
    }

    // Getting the flights using pagination.
    @GetMapping("")
    public Page<Flight> getAllFlights(@RequestParam int page, @RequestParam int size){
        return flightRepository.findAll(PageRequest.of(page, size, Sort.by("fromAirport").ascending()));
    }

    @GetMapping("/status")
    public List<Flight> getAllFlightsByStatus(){
        return flightRepository.findByFlightStatus(FlightStatus.ONTIME);
    }

    @GetMapping("/custom")
    public List<Flight> getCustomFlight(@RequestParam FlightStatus p1, @RequestParam FlightStatus p2){
        return flightRepository.getCustomFlight(p1, p2);
    }
}

