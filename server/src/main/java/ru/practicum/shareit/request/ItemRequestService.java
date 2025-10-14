package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;

import java.util.List;

public interface ItemRequestService {
    ItemRequest createRequest(ItemRequestDto itemRequestDto, Long userId);

    List<ItemRequest> getAllRequests(Long userId);

    ItemRequestDtoResponse getRequestById(Long userId, Long requestId);

    List<ItemRequestDtoResponse> getRequestsByUser(Long userId);


}
