package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.ItemRequestFeignClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestFeignClient itemRequestFeignClient;

    @Autowired
    public ItemRequestController(ItemRequestFeignClient itemRequestFeignClient) {
        this.itemRequestFeignClient = itemRequestFeignClient;
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestFeignClient.createRequest(itemRequestDto, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestFeignClient.getAllItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long id) {
        return itemRequestFeignClient.getRequestById(userId, id);
    }

    @GetMapping
    public ResponseEntity<List<Object>> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestFeignClient.getRequestsByUser(userId);
    }
}
