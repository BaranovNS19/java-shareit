package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ItemRequestStorage {
    private final HashMap<Long, ItemRequest> requests = new HashMap<>();
    private static Long requestId = 0L;

    public ItemRequest addRequest(ItemRequest itemRequest) {
        requestId++;
        itemRequest.setId(requestId);
        requests.put(itemRequest.getId(), itemRequest);
        return itemRequest;
    }

    public ItemRequest getItemRequestByDescription(String description) {
        for (ItemRequest ir : requests.values()) {
            if (ir.getDescription().equals(description)) {
                return ir;
            }
        }
        return null;
    }
}
