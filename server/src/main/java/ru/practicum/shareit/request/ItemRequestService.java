package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;

import java.util.List;

public interface ItemRequestService {
    ItemRequest createRequest(ItemRequestDto itemRequestDto, Long userId);

    Page<ItemRequestDtoResponse> getAllRequests(Long userId, PageRequest pageRequest);

    ItemRequestDtoResponse getRequestById(Long userId, Long requestId);

    List<ItemRequestDtoResponse> getRequestsByUser(Long userId);


}
