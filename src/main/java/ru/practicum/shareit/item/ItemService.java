package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto getItem(Long id, Long userId);

    List<ItemDto> getItemsByUser(Long id);

    List<ItemDto> getItemsByText(String text);

    ItemDto updateItem(Long userId, Long id, ItemDto itemDto);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}
