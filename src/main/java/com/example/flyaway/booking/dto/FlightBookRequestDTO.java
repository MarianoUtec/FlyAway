package com.example.flyaway.booking.dto;

import jakarta.validation.constraints.NotNull;

public record FlightBookRequestDTO(

        @NotNull
        Long flightId

) {
}
