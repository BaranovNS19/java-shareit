package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemStorage fakeItemRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemStorage fakeItemRepository, ItemMapper itemMapper) {
        this.fakeItemRepository = fakeItemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto createItem(ItemDto item, Long userId) {
        return ItemDto.toItemDto(fakeItemRepository.addItem(itemMapper.toItem(item, userId), userId));
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
