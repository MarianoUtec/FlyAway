package com.example.flyaway.flight.dto;

import com.example.flyaway.common.dto.NewIdDTO;

import java.util.List;

public record CreateManyFlightsResponseDTO(

        List<NewIdDTO> items
) {
}
