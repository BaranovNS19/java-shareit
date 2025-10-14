package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ItemRequestService itemRequestService;
    private final ItemRequestMapper itemRequestMapper;

    @Autowired
    public ItemMapper(UserService userService, CommentRepository commentRepository, CommentMapper commentMapper,
                      @Lazy ItemRequestService itemRequestService, @Lazy ItemRequestMapper itemRequestMapper) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.itemRequestService = itemRequestService;
        this.itemRequestMapper = itemRequestMapper;
    }

    public Item toItem(ItemDto itemDto, Long userId) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(userService.getUser(userId));
        if (itemDto.getRequestId() != null) {
            item.setRequest(itemRequestMapper.toItemRequest(itemRequestService.getRequestById(userId, itemDto.getRequestId())));
        }

        return item;
    }

    public ItemDto toItemDto(Item item) {
        List<Comment> comments = commentRepository.findByItemIdOrderByCreatedDesc(item.getId());
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment c : comments) {
            commentDtos.add(commentMapper.toCommentDto(c));
        }
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        if (itemDto.getRequestId() != null) {
            itemDto.setRequestId(item.getRequest().getId());
        }
        itemDto.setComments(commentDtos);
        return itemDto;
    }
}
