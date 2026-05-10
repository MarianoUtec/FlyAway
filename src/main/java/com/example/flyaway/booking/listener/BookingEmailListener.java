package com.example.flyaway.booking.listener;

import com.example.flyaway.booking.domain.Booking;
import com.example.flyaway.booking.domain.BookingCreatedEvent;
import com.example.flyaway.booking.infrastructure.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class BookingEmailListener {

    private final BookingRepository bookingRepository;

    @Async
    @EventListener
    @SneakyThrows
    public void handleBookingCreated(
            BookingCreatedEvent event
    ) {

        Booking booking = bookingRepository
                .findById(event.getBookingId())
                .orElseThrow();

        String currentDirectory =
                System.getProperty("user.dir");

        Path path = Paths.get( //PATH EXACTO PARA PROBAR EN TEST CASES DE OTRO REPO
                "C:\\Users\\esanc\\OneDrive\\Datos adjuntos\\Desktop\\Utec Tareas\\202601\\DBP\\-cs2031-2026-1-week07-tester-main\\-cs2031-2026-1-week07-tester-main\\target\\flight_booking_email_" +
                        booking.getId() +
                        ".txt"
        );

        Files.createDirectories(path.getParent());

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd'T'HH:mm:ss"
                ); //Para que no de error al comparar fechas guardadas en el txt

        String content = """
                Flight booking confirmation
                
                bookingDate: %s
                customerFirstName: %s
                customerLastName: %s
                flightNumber: %s
                estDepartureTime: %s
                estArrivalTime: %s
                """
                .formatted(
                        booking.getBookingTime(),
                        booking.getUser().getFirstName(),
                        booking.getUser().getLastName(),
                        booking.getFlight().getFlightNumber(),
                        booking.getFlight().getEstDepartureTime().format(formatter),
                        booking.getFlight().getEstArrivalTime().format(formatter)
                );

        Files.writeString(path, content);
    }
}