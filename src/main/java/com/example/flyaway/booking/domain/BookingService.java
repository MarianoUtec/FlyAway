package com.example.flyaway.booking.domain;

import com.example.flyaway.booking.dto.BookingDetailDTO;
import com.example.flyaway.booking.dto.BookingDetailFetchDTO;
import com.example.flyaway.booking.dto.BookingResponseDTO;
import com.example.flyaway.booking.dto.FlightBookRequestDTO;
import com.example.flyaway.booking.infrastructure.BookingRepository;
import com.example.flyaway.common.dto.NewIdDTO;
import com.example.flyaway.common.exception.BadRequestException;
import com.example.flyaway.flight.domain.Flight;
import com.example.flyaway.flight.domain.FlightService;
import com.example.flyaway.user.domain.User;
import com.example.flyaway.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final FlightService flightService;

    private final UserService userService;

    public NewIdDTO create(FlightBookRequestDTO request) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Flight flight = flightService.findById(request.flightId());

        if (flight.getAvailableSeats() <= 0) {
            throw new BadRequestException(
                    "No available seats"
            );
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        flightService.save(flight);

        Booking booking = new Booking(
                user,
                flight,
                LocalDateTime.now()
        );

        Booking savedBooking =
                bookingRepository.save(booking);

        return new NewIdDTO(savedBooking.getId());
    }

    public BookingResponseDTO myBookings() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        List<BookingDetailDTO> bookings =
                bookingRepository.findByUser(user)
                        .stream()
                        .map(booking ->
                                new BookingDetailDTO(
                                        booking.getId(),
                                        booking.getFlight().getId(),
                                        booking.getFlight().getAirlineName(),
                                        booking.getFlight().getFlightNumber(),
                                        booking.getFlight().getEstDepartureTime(),
                                        booking.getFlight().getEstArrivalTime(),
                                        booking.getBookingTime()
                                )
                        )
                        .toList();

        return new BookingResponseDTO(bookings);
    }

    public BookingDetailFetchDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new com.example.flyaway.common.exception.BadRequestException(
                        "Booking not found"
                ));

        return new BookingDetailFetchDTO(
                booking.getId(),
                booking.getBookingTime(),
                booking.getFlight().getId(),
                booking.getFlight().getFlightNumber(),
                booking.getUser().getId(),
                booking.getUser().getFirstName(),
                booking.getUser().getLastName()
        );
    }

    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}