package ru.practicum.shareit.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.State;

import java.util.List;

@FeignClient(
        name = "bookingService",
        url = "${shareit-server.url}",
        path = "/bookings"
)
public interface BookingFeignClient {
    @PostMapping
    ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody @Valid BookingDto bookingDto);

    @PatchMapping("/{bookingId}")
    ResponseEntity<Object> approveBooking(@PathVariable Long bookingId, @RequestParam boolean approved,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId);

    @GetMapping("/{bookingId}")
    ResponseEntity<Object> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId);

    @GetMapping
    ResponseEntity<List<Object>> getBookingsByUser(@RequestParam(required = false, defaultValue = "ALL") State state,
                                                          @RequestHeader("X-Sharer-User-Id") Long userId);

    @GetMapping("/owner")
    ResponseEntity<List<Object>> getBookingByOwner(@RequestParam(required = false, defaultValue = "ALL") State state,
                                                          @RequestHeader("X-Sharer-User-Id") Long userId);
}
