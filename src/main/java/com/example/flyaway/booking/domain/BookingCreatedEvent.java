package com.example.flyaway.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingCreatedEvent {

    private Long bookingId;

}
