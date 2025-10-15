package ru.practicum.shareit.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@FeignClient(
        name = "itemService",
        url = "${shareit-server.url}",
        path = "/items"
)
public interface ItemFeignClient {

    @PostMapping
    ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody @Valid ItemDto itemDto);

    @GetMapping("/{id}")
    ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Object>> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId);

    @GetMapping("/search")
    ResponseEntity<List<Object>> getItemsByText(@RequestParam String text);

    @PatchMapping("/{id}")
    ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id,
                                      @RequestBody ItemDto itemDto);

    @PostMapping("/{itemId}/comment")
    ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                                      @RequestBody @Valid CommentDto commentDto);
}
