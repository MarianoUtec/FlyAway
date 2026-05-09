package com.example.flyaway.flight.dto;

import java.util.List;

public record FlightSearchResponseDTO(

        List<FlightSearchItemDTO> items

) {
}
