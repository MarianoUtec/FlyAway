package com.example.flyaway.booking.domain;

import com.example.flyaway.flight.domain.Flight;
import com.example.flyaway.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    public Booking(
            User user,
            Flight flight,
            LocalDateTime bookingTime
    ) {
        this.user = user;
        this.flight = flight;
        this.bookingTime = bookingTime;
    }
}
