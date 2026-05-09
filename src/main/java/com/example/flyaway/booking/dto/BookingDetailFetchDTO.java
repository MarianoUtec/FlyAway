package com.example.flyaway.booking.dto;

import java.time.LocalDateTime;

public record BookingDetailFetchDTO(
        Long id,
        LocalDateTime bookingDate,
        Long flightId,
        String flightNumber,
        Long customerId,
        String customerFirstName,
        String customerLastName
) {
}

