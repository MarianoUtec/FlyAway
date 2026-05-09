package com.example.flyaway.flight.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false, unique = true, length = 6)
    @Pattern(
            regexp = "^[A-Z]{2,3}[0-9]{3}$",
            message = "Flight number must contain only A-Z and 0-9, max 6 characters"
    )
    private String flightNumber;

    @Column(nullable = false)
    private LocalDateTime estDepartureTime;

    @Column(nullable = false)
    private LocalDateTime estArrivalTime;

    @Column(nullable = false)
    @Min(value = 1, message = "Available seats must be greater than 0")
    private Integer availableSeats;

    public Flight(
            String airlineName,
            String flightNumber,
            LocalDateTime estDepartureTime,
            LocalDateTime estArrivalTime,
            Integer availableSeats
    ) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.estDepartureTime = estDepartureTime;
        this.estArrivalTime = estArrivalTime;
        this.availableSeats = availableSeats;
    }
}
