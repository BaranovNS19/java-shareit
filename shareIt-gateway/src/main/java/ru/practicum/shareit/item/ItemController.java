package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.ItemFeignClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemFeignClient itemFeignClient;

    @Autowired
    public ItemController(ItemFeignClient itemFeignClient) {
        this.itemFeignClient = itemFeignClient;
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody @Valid ItemDto itemDto) {
        return itemFeignClient.createItem(userId, itemDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id) {
        return itemFeignClient.getItem(userId, id);
    }

    @GetMapping
    public ResponseEntity<List<Object>> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemFeignClient.getItemsByUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Object>> getItemsByText(@RequestParam String text) {
        return itemFeignClient.getItemsByText(text);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long id,
                                             @RequestBody ItemDto itemDto) {
        return itemFeignClient.updateItem(userId, id, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                                             @RequestBody @Valid CommentDto commentDto) {
        return itemFeignClient.addComment(userId, itemId, commentDto);
    }
}
