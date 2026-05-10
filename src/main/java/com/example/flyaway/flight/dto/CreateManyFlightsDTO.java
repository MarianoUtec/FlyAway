package com.example.flyaway.flight.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateManyFlightsDTO(

        @Valid
        @NotEmpty(message = "Inputs cannot be empty")
        List<CreateFlightDTO> inputs
) {
}
