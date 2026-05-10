package com.example.flyaway.flight.domain;

import com.example.flyaway.common.dto.NewIdDTO;
import com.example.flyaway.common.exception.BadRequestException;
import com.example.flyaway.common.exception.ConflictException;
import com.example.flyaway.common.exception.ResourceNotFoundException;
import com.example.flyaway.flight.dto.*;
import com.example.flyaway.flight.infrastructure.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public NewIdDTO create(CreateFlightDTO request) {

        if (
                request.estDepartureTime()
                        .isAfter(request.estArrivalTime())
        ) {
            throw new BadRequestException(
                    "Departure time must be before arrival time"
            );
        }

        if (
                request.estDepartureTime()
                        .isEqual(request.estArrivalTime())
        ) {
            throw new BadRequestException(
                    "Departure time must be before arrival time"
            );
        }

        if (
                flightRepository.existsByFlightNumber(
                        request.flightNumber()
                )
        ) {
            throw new ConflictException(
                    "Flight number already exists"
            );
        }

        Flight flight = new Flight(
                request.airlineName(),
                request.flightNumber(),
                request.estDepartureTime(),
                request.estArrivalTime(),
                request.availableSeats()
        );

        Flight savedFlight =
                flightRepository.save(flight);

        return new NewIdDTO(savedFlight.getId());
    }

    public FlightSearchResponseDTO search(
            String flightNumber,
            String airlineName,
            LocalDateTime estDepartureTimeFrom,
            LocalDateTime estDepartureTimeTo
    ) {

        List<Flight> flights = flightRepository.findAll();

        if (
                flightNumber != null
                        && !flightNumber.isBlank()
        ) {
            flights = flights.stream()
                    .filter(flight ->
                            flight.getFlightNumber()
                                    .toLowerCase()
                                    .contains(
                                            flightNumber.toLowerCase()
                                    )
                    )
                    .toList();
        }

        if (
                airlineName != null
                        && !airlineName.isBlank()
        ) {
            flights = flights.stream()
                    .filter(flight ->
                            flight.getAirlineName()
                                    .toLowerCase()
                                    .contains(
                                            airlineName.toLowerCase()
                                    )
                    )
                    .toList();
        }

        if (estDepartureTimeFrom != null) {

            flights = flights.stream()
                    .filter(flight ->
                            !flight.getEstDepartureTime()
                                    .isBefore(estDepartureTimeFrom)
                    )
                    .toList();
        }

        if (estDepartureTimeTo != null) {

            flights = flights.stream()
                    .filter(flight ->
                            !flight.getEstDepartureTime()
                                    .isAfter(estDepartureTimeTo)
                    )
                    .toList();
        }

        List<FlightSearchItemDTO> response =
                flights.stream()
                        .map(flight ->
                                new FlightSearchItemDTO(
                                        flight.getId(),
                                        flight.getAirlineName(),
                                        flight.getFlightNumber(),
                                        flight.getEstDepartureTime(),
                                        flight.getEstArrivalTime(),
                                        flight.getAvailableSeats()
                                )
                        )
                        .toList();

        return new FlightSearchResponseDTO(response);
    }

    public Flight findById(Long id) {

        return flightRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Flight not found"
                        )
                );
    }

    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    public void deleteAll() {
        flightRepository.deleteAll();
    }

    public CreateManyFlightsResponseDTO createMany(
            CreateManyFlightsDTO request
    ) {

        List<NewIdDTO> createdFlights =
                request.inputs()
                        .stream()
                        .map(this::create)
                        .toList();

        return new CreateManyFlightsResponseDTO(
                createdFlights
        );
    }
}
