package com.example.flyaway.flight.application;

import com.example.flyaway.booking.domain.BookingService;
import com.example.flyaway.booking.dto.BookingDetailFetchDTO;
import com.example.flyaway.booking.dto.FlightBookRequestDTO;
import com.example.flyaway.common.dto.NewIdDTO;
import com.example.flyaway.flight.domain.FlightService;
import com.example.flyaway.flight.dto.CreateFlightDTO;
import com.example.flyaway.flight.dto.FlightSearchResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<NewIdDTO> create(
            @Valid @RequestBody CreateFlightDTO request
    ) {

        return ResponseEntity.status(201).body(
                flightService.create(request)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponseDTO> search(

            @RequestParam(required = false)
            String flightNumber,

            @RequestParam(required = false)
            String airlineName,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime estDepartureTimeFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime estDepartureTimeTo
    ) {

        return ResponseEntity.ok(
                flightService.search(
                        flightNumber,
                        airlineName,
                        estDepartureTimeFrom,
                        estDepartureTimeTo
                )
        );
    }

    @PostMapping("/book")
    public ResponseEntity<NewIdDTO> book(
            @Valid @RequestBody FlightBookRequestDTO request
    ) {
        return ResponseEntity.status(201).body(
                bookingService.create(request)
        );
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingDetailFetchDTO> getBooking(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }
}
