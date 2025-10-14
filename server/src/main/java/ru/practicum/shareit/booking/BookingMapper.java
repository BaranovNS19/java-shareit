package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

@Component
public class BookingMapper {

    private final ItemService itemService;
    private final UserService userService;
    private final ItemMapper itemMapper;

    @Autowired
    public BookingMapper(ItemService itemService, UserService userService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.userService = userService;
        this.itemMapper = itemMapper;
    }

    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getItem().getId(), booking.getStart(), booking.getEnd());
    }

    public Booking toBooking(BookingDto bookingDto, Long userId, Status status) {
        return new Booking(bookingDto.getId(), bookingDto.getStart(), bookingDto.getEnd(),
                itemMapper.toItem(itemService.getItem(bookingDto.getItemId(), userId), userId),
                userService.getUser(userId), status);
    }
}
