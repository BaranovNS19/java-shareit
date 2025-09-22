package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.FakeUserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class FakeItemRepository {
    private final HashMap<Long, Item> items = new HashMap<>();
    private static Long itemId = 0L;
    private final FakeUserRepository fakeUserRepository;

    @Autowired
    public FakeItemRepository(FakeUserRepository fakeUserRepository) {
        this.fakeUserRepository = fakeUserRepository;
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
                itemsByUser.add(ItemDto.toItemDto(i));
            }
        }
        return itemsByUser;
    }

    public List<ItemDto> getItemsByText(String text) {
        List<ItemDto> itemsByText = new ArrayList<>();
        if (text != null && !text.isBlank()) {
            for (Item i : items.values()) {
                if ((i.getName().toLowerCase().contains(text.toLowerCase()) || i.getDescription().toLowerCase()
                        .contains(text.toLowerCase())) && i.getAvailable()) {
                    itemsByText.add(ItemDto.toItemDto(i));
                }
            }
            return itemsByText;
        }
        return itemsByText;
    }

    public Item updateItem(Long userId, Long id, ItemDto itemDto) {
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
