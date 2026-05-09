package com.example.flyaway.flight.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record CreateFlightDTO(

        @NotBlank
        String airlineName,

        @NotBlank
        @Pattern(
                regexp = "^[A-Z]{2,3}[0-9]{3}$",
                message = "Flight number must contain only A-Z and 0-9, max 6 characters"
        )
        String flightNumber,

        @NotNull
        LocalDateTime estDepartureTime,

        @NotNull
        LocalDateTime estArrivalTime,

        @NotNull
        @Min(value = 1, message = "Available seats must be greater than 0")
        Integer availableSeats

) {
}
