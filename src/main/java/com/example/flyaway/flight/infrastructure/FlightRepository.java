package com.example.flyaway.flight.infrastructure;

import com.example.flyaway.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByFlightNumberContainingIgnoreCase(
            String flightNumber
    );

    List<Flight> findByAirlineNameContainingIgnoreCase(
            String airlineName
    );

    List<Flight> findByEstDepartureTimeBetween(
            LocalDateTime from,
            LocalDateTime to
    );
}
