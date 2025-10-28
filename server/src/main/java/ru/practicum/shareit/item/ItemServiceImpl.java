package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;
    private final ItemRequestService itemRequestService;
    private final ItemRequestMapper itemRequestMapper;

    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper, ItemRepository itemRepository, UserService userService, CommentRepository commentRepository, CommentMapper commentMapper, BookingRepository bookingRepository, ItemRequestService itemRequestService, ItemRequestMapper itemRequestMapper) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.bookingRepository = bookingRepository;
        this.itemRequestService = itemRequestService;
        this.itemRequestMapper = itemRequestMapper;
    }

    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User owner = userService.getUser(userId);
        Item itemMap = itemMapper.toItem(itemDto, owner, null);
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestService.getRequestById(userId,
                    itemDto.getRequestId()));
            itemMap.setRequest(itemRequest);
        }
        itemMap.setOwner(owner);
        itemRepository.save(itemMap);
        List<Comment> comments = commentRepository.findByItemIdOrderByCreatedDesc(itemMap.getId());
        return itemMapper.toItemDto(itemMap, comments);
    }

    @Override
    public ItemDto getItem(Long id, Long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вещь с id [" + id + "] не найдена"));
        List<Comment> comments = commentRepository.findByItemIdOrderByCreatedDesc(item.getId());
        return itemMapper.toItemDto(item, comments);
    }

    @Override
    public List<ItemDto> getItemsByUser(Long id) {
        List<ItemDto> result = new ArrayList<>();
        for (Item i : itemRepository.findByOwnerId(id)) {
            result.add(itemMapper.toItemDto(i, commentRepository.findByItemIdOrderByCreatedDesc(i.getId())));
        }
        return result;
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        List<ItemDto> result = new ArrayList<>();
        for (Item i : itemRepository.searchAvailableItemsByText(text)) {
            result.add(itemMapper.toItemDto(i, commentRepository.findByItemIdOrderByCreatedDesc(i.getId())));
        }
        return result;
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {
        User user = userService.getUser(userId);
        ItemRequest itemRequest = null;
        if (itemDto.getRequestId() != null) {
            itemRequest = itemRequestMapper.toItemRequest(itemRequestService.getRequestById(userId,
                    itemDto.getRequestId()));
        }
        if (!Objects.equals(itemMapper.toItem(itemDto, user, itemRequest).getOwner().getId(), userId)) {
            throw new ForbiddenException("пользователь [" + userId + "] не является владельцем вещи [" + id + "]");
        }
        Item item = itemMapper.toItem(getItem(id, userId), user, itemRequest);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        List<Comment> comments = commentRepository.findByItemIdOrderByCreatedDesc(item.getId());
        return itemMapper.toItemDto(item, comments);
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        User user = userService.getUser(userId);
        ItemRequest itemRequest = null;
        if (getItem(itemId, userId).getRequestId() != null) {
            itemRequest = itemRequestMapper.toItemRequest(itemRequestService.getRequestById(userId,
                    getItem(itemId, userId).getRequestId()));
        }
        Item item = itemMapper.toItem(getItem(itemId, userId), user, itemRequest);
        List<Booking> userBookings = bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.APPROVED);
        boolean hasCompletedBooking = userBookings.stream()
                .anyMatch(booking ->
                        booking.getItem().getId().equals(itemId) &&
                                booking.getEnd().isBefore(LocalDateTime.now())
                );

        if (!hasCompletedBooking) {
            throw new BadRequestException("Пользователь [" + userId + "] не бронировал вещь [" + itemId + "] или бронирование еще не завершено");
        }

        Comment comment = commentMapper.toComment(commentDto, item, user);
        comment.setCreated(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        CommentDto resultDto = commentMapper.toCommentDto(savedComment);
        resultDto.setAuthorName(user.getName());
        return resultDto;
    }
}
