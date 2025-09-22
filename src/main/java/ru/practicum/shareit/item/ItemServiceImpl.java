package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final FakeItemRepository fakeItemRepository;

    @Autowired
    public ItemServiceImpl(FakeItemRepository fakeItemRepository) {
        this.fakeItemRepository = fakeItemRepository;
    }

    @Override
    public ItemDto createItem(Item item, Long userId) {
        return ItemDto.toItemDto(fakeItemRepository.addItem(item, userId));
    }

    @Override
    public ItemDto getItem(Long id, Long userId) {
        return ItemDto.toItemDto(fakeItemRepository.getItem(id));
    }

    @Override
    public List<ItemDto> getItemsByUser(Long id) {
        return fakeItemRepository.getItemsByUser(id);
    }

    @Override
    public List<ItemDto> getItemsByText(String text) {
        return fakeItemRepository.getItemsByText(text);
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {
        return ItemDto.toItemDto(fakeItemRepository.updateItem(userId, id, itemDto));
    }
}
