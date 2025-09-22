package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class FakeItemRequestRepository {
    private final HashMap<Long, ItemRequest> requests = new HashMap<>();
    private static Long requestId = 0L;

    public ItemRequest addRequest(ItemRequest itemRequest) {
        requestId++;
        itemRequest.setId(requestId);
        requests.put(itemRequest.getId(), itemRequest);
        return itemRequest;
    }
}
