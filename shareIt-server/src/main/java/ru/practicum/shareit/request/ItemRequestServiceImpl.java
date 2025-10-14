package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestStorage fakeItemRequestRepository;
    private final ItemRequestMapper mapper;
    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestStorage fakeItemRequestRepository, ItemRequestMapper mapper, ItemRequestRepository itemRequestRepository, UserService userService) {
        this.fakeItemRequestRepository = fakeItemRequestRepository;
        this.mapper = mapper;
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
    }

    @Override
    public ItemRequest createRequest(ItemRequestDto itemRequestDto, Long userId) {
        return itemRequestRepository.save(mapper.toItemRequest(itemRequestDto, userId));
    }

    @Override
    public List<ItemRequest> getAllRequests(Long userId) {
        userService.getUser(userId);
        return itemRequestRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public ItemRequestDtoResponse getRequestById(Long userId, Long requestId) {
        userService.getUser(userId);
        return mapper.toItemRequestDtoResponse(itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id [" + requestId + "] не найден")));
    }

    @Override
    public List<ItemRequestDtoResponse> getRequestsByUser(Long userId) {
        userService.getUser(userId);
        List<ItemRequestDtoResponse> result = new ArrayList<>();
        for (ItemRequest ir : itemRequestRepository.findByUserId(userId)) {
            result.add(mapper.toItemRequestDtoResponse(ir));
        }
        return result;
    }
}
