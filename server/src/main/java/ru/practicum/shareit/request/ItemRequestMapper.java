package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemRequestMapper {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemRequestMapper(UserService userService, ItemRepository itemRepository, ItemMapper itemMapper) {
        this.userService = userService;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        return new ItemRequest(itemRequestDto.getId(), itemRequestDto.getDescription(), userService.getUser(userId),
                LocalDateTime.now());
    }

    public ItemRequestDtoResponse toItemRequestDtoResponse(ItemRequest itemRequest) {
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item i : itemRepository.findByRequestId(itemRequest.getId())) {
            itemDtos.add(itemMapper.toItemDto(i));
        }
        return new ItemRequestDtoResponse(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getRequestor(),
                itemRequest.getCreated(), itemDtos);
    }

    public ItemRequest toItemRequest(ItemRequestDtoResponse itemRequestDtoResponse) {
        return new ItemRequest(itemRequestDtoResponse.getId(), itemRequestDtoResponse.getDescription(), itemRequestDtoResponse.getRequestor(),
                LocalDateTime.now());
    }
}
