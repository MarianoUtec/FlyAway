package com.example.flyaway.booking.infrastructure;

import com.example.flyaway.booking.domain.Booking;
import com.example.flyaway.flight.domain.Flight;
import com.example.flyaway.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByFlight(Flight flight);
}
