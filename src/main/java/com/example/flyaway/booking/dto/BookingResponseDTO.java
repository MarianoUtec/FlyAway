package com.example.flyaway.booking.dto;

import java.util.List;

public record BookingResponseDTO(

        List<BookingDetailDTO> bookings

) {
}
