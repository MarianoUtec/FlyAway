package com.example.flyaway.cleanup.domain;

import com.example.flyaway.booking.infrastructure.BookingRepository;
import com.example.flyaway.flight.infrastructure.FlightRepository;
import com.example.flyaway.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CleanupService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public CleanupService(
            BookingRepository bookingRepository,
            FlightRepository flightRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    public void cleanup() {

        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
    }
}
