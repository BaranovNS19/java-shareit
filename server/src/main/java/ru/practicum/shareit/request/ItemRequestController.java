package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequest createRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.createRequest(itemRequestDto, userId);
    }

    @GetMapping("/all")
    public List<ItemRequest> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAllRequests(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDtoResponse getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long id) {
        return itemRequestService.getRequestById(userId, id);
    }

    @GetMapping
    public List<ItemRequestDtoResponse> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getRequestsByUser(userId);
    }
}
