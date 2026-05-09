package com.example.flyaway.booking.application;

import com.example.flyaway.booking.domain.BookingService;
import com.example.flyaway.booking.dto.BookingResponseDTO;
import com.example.flyaway.booking.dto.FlightBookRequestDTO;
import com.example.flyaway.common.dto.NewIdDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<NewIdDTO> book(
            @Valid @RequestBody FlightBookRequestDTO request
    ) {

        return ResponseEntity.status(201).body(
                bookingService.create(request)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<BookingResponseDTO> myBookings() {

        return ResponseEntity.ok(
                bookingService.myBookings()
        );
    }
}
