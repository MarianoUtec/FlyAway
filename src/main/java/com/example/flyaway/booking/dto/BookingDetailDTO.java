package com.example.flyaway.booking.dto;

import java.time.LocalDateTime;

public record BookingDetailDTO(

        Long bookingId,

        Long flightId,

        String airlineName,

        String flightNumber,

        LocalDateTime estDepartureTime,

        LocalDateTime estArrivalTime,

        LocalDateTime bookingTime

) {
}
