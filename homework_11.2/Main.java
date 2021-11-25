import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ;

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime hoursDifference = currentTime.plusHours(2);
        List<Flight> flightsIn2Hours = new ArrayList<>();
        List<Flight> flights = new ArrayList<>();
        airport.getTerminals().stream()
                .forEach(t -> t.getFlights()
                                .forEach(f -> flights.add(f)));
        flightsIn2Hours = flights.stream()
                .filter(f -> hoursDifference.compareTo(f.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) >= 0 && f.getType() == Flight.Type.DEPARTURE)
                .collect(Collectors.toList());
        return flightsIn2Hours;

    }
}

