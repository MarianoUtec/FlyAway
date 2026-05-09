package com.example.flyaway.flight.dto;

import java.time.LocalDateTime;

public record FlightResponseDTO(

        Long id,

        String airlineName,

        String flightNumber,

        LocalDateTime estDepartureTime,

        LocalDateTime estArrivalTime,

        Integer availableSeats

) {
}
