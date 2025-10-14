package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody @Valid BookingDto bookingDto) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBooking(@PathVariable Long bookingId, @RequestParam boolean approved,
                                  @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getBookingsByUser(@RequestParam(required = false, defaultValue = "ALL") State state,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingByOwner(@RequestParam(required = false, defaultValue = "ALL") State state,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingByOwner(userId, state);
    }
}
