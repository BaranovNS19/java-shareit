package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestStorage;
import ru.practicum.shareit.user.UserStorage;

@Component
public class ItemMapper {

    private final UserStorage fakeUserRepository;
    private final ItemRequestStorage fakeItemRequestRepository;

    @Autowired
    public ItemMapper(UserStorage fakeUserRepository, ItemRequestStorage fakeItemRequestRepository) {
        this.fakeUserRepository = fakeUserRepository;
        this.fakeItemRequestRepository = fakeItemRequestRepository;
    }

    public Item toItem(ItemDto itemDto, Long userId) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(),
                fakeUserRepository.getUserById(userId), fakeItemRequestRepository.getItemRequestByDescription(itemDto
                .getDescription()));
    }
}
