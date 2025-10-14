package ru.practicum.shareit.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@FeignClient(
        name = "itemRequestService",
        url = "${shareit-server.url}",
        path = "/requests"
)
public interface ItemRequestFeignClient {
    @PostMapping
    ResponseEntity<Object> createRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") Long userId);

    @GetMapping("/all")
    ResponseEntity<List<Object>> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId);

    @GetMapping("/{id}")
    ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Object>> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId);
}
