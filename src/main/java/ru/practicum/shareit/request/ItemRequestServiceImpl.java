package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestStorage fakeItemRequestRepository;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestStorage fakeItemRequestRepository) {
        this.fakeItemRequestRepository = fakeItemRequestRepository;
    }

    @Override
    public ItemRequestDto createRequest(ItemRequest itemRequest) {
        return ItemRequestMapper.toItemRequestDto(fakeItemRequestRepository.addRequest(itemRequest));
    }
}
