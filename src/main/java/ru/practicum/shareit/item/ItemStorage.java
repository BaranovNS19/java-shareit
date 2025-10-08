package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Deprecated
@Repository
public class ItemStorage {
    private final HashMap<Long, Item> items = new HashMap<>();
    private static Long itemId = 0L;
    private final UserStorage fakeUserRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemStorage(UserStorage fakeUserRepository, ItemMapper itemMapper) {
        this.fakeUserRepository = fakeUserRepository;
        this.itemMapper = itemMapper;
    }

    public Item addItem(Item item, Long userId) {
        itemId++;
        item.setId(itemId);
        item.setOwner(fakeUserRepository.getUserById(userId));
        items.put(item.getId(), item);
        return item;
    }

    public Item getItem(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с id [" + id + "] не найдена");
        }
        return items.get(id);
    }

    public List<ItemDto> getItemsByUser(Long id) {
        List<ItemDto> itemsByUser = new ArrayList<>();
        for (Item i : items.values()) {
            if (Objects.equals(i.getOwner().getId(), id)) {
                itemsByUser.add(itemMapper.toItemDto(i));
            }
        }
        return itemsByUser;
    }

    public List<ItemDto> getItemsByText(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        List<ItemDto> itemsByText = new ArrayList<>();
        String lowerText = text.toLowerCase();
        for (Item i : items.values()) {
            if ((i.getName().toLowerCase().contains(lowerText) || i.getDescription().toLowerCase()
                    .contains(lowerText)) && i.getAvailable()) {
                itemsByText.add(itemMapper.toItemDto(i));
            }
        }
        return itemsByText;
    }

    public Item updateItem(Long userId, Long id, ItemDto itemDto) {
        if (!Objects.equals(itemMapper.toItem(itemDto, userId).getOwner().getId(), userId)) {
            throw new ForbiddenException("пользователь [" + userId + "] не является владельцем вещи [" + id + "]");
        }
        fakeUserRepository.getUserById(userId);
        Item oldItem = getItem(id);
        if (itemDto.getName() != null) {
            oldItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            oldItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            oldItem.setAvailable(itemDto.getAvailable());
        }
        return oldItem;
    }
}
