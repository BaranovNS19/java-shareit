package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    Booking createBooking(Long userId, BookingDto bookingDto);

    Booking approveBooking(Long bookingId, boolean approve, Long userId);

    Booking getBookingById(Long userId, Long bookingId);

    List<Booking> getBookingsByUser(Long userId, State state);

    List<Booking> getBookingByOwner(Long userId, State state);
}
