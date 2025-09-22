package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Service
public class ItemRequestServiceImpl implements ItemRequestService{

    private final FakeItemRequestRepository fakeItemRequestRepository;

    @Autowired
    public ItemRequestServiceImpl(FakeItemRequestRepository fakeItemRequestRepository) {
        this.fakeItemRequestRepository = fakeItemRequestRepository;
    }

    @Override
    public ItemRequestDto createRequest(ItemRequest itemRequest) {
        return ItemRequestDto.toItemRequestDto(fakeItemRequestRepository.addRequest(itemRequest));
    }
}
