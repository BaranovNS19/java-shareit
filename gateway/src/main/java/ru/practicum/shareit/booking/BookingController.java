package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.State;
import ru.practicum.shareit.client.BookingFeignClient;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingFeignClient bookingFeignClient;

    @Autowired
    public BookingController(BookingFeignClient bookingFeignClient) {
        this.bookingFeignClient = bookingFeignClient;
    }

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestBody @Valid BookingDto bookingDto) {
        return bookingFeignClient.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@PathVariable Long bookingId, @RequestParam boolean approved,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingFeignClient.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        return bookingFeignClient.getBookingById(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<List<Object>> getBookingsByUser(@RequestParam(required = false, defaultValue = "ALL") State state,
                                                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingFeignClient.getBookingsByUser(state, userId);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Object>> getBookingByOwner(@RequestParam(required = false, defaultValue = "ALL") State state,
                                                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingFeignClient.getBookingByOwner(state, userId);
    }
}
