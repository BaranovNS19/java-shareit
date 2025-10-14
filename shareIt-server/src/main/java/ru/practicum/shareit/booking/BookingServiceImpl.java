package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public BookingServiceImpl(BookingMapper bookingMapper, BookingRepository bookingRepository, UserService userService, ItemService itemService, ItemMapper itemMapper) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional
    public Booking createBooking(Long userId, BookingDto bookingDto) {
        User user = userService.getUser(userId);
        Item item = itemMapper.toItem(itemService.getItem(bookingDto.getItemId(), userId), userId);
        if (!item.getAvailable() || bookingDto.getStart().isBefore(LocalDateTime.now()) ||
                bookingDto.getEnd().isBefore(LocalDateTime.now()) || bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new BadRequestException("Вещь [" + item.getId() + "] недоступна для бронирования");
        }

        for (ItemDto itemDto : itemService.getItemsByUser(userId)) {
            if (Objects.equals(itemMapper.toItem(itemDto, userId).getOwner().getId(), userId)) {
                throw new ForbiddenException("собственные вещи недоступны для бронирования");
            }
        }
        Booking booking = bookingMapper.toBooking(bookingDto, userId, Status.WAITING);
        booking.setBooker(user);
        booking.setItem(item);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    @Transactional
    public Booking approveBooking(Long bookingId, boolean approve, Long userId) {
        Booking booking = getBookingById(bookingId);
        if (!Objects.equals(booking.getItem().getOwner().getId(), userId)) {
            throw new ForbiddenException("Пользователь [" + userId + "] не является владельцем вещи [" + booking
                    .getItem().getId() + "]");
        }
        userService.getUser(userId);

        if (approve) {
            booking.setStatus(Status.APPROVED);
            itemService.updateItem(userId, booking.getItem().getId(), new ItemDto(null, null, null,
                    false, null, null, null, null));
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return booking;
    }

    @Override
    public Booking getBookingById(Long userId, Long bookingId) {
        Booking booking = getBookingById(bookingId);
        if (!Objects.equals(booking.getItem().getOwner().getId(), userId)
                && !Objects.equals(booking.getBooker().getId(), userId)) {
            throw new ForbiddenException("пользователь не является атором бронирования и владельцем вещи");
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId, State state) {
        userService.getUser(userId);
        switch (state) {
            case ALL -> {
                return bookingRepository.findByBookerIdOrderByStartDesc(userId);
            }
            case REJECTED -> {
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            }
            case PAST -> {
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.APPROVED);
            }
        }
        return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
    }

    @Override
    public List<Booking> getBookingByOwner(Long userId, State state) {
        userService.getUser(userId);
        if (state == null) {
            return bookingRepository.findByItemOwnerIdOrderByStartDesc(userId);
        }
        if (state.equals(State.REJECTED)) {
            bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
        }
        return bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
    }

    private Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("бронь с id [" + id + "] не найдена"));
    }
}
