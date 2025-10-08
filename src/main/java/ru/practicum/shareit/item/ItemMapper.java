package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestStorage;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {

    private final UserService userService;
    private final ItemRequestStorage fakeItemRequestRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public ItemMapper(UserService userService, ItemRequestStorage fakeItemRequestRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.userService = userService;
        this.fakeItemRequestRepository = fakeItemRequestRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public Item toItem(ItemDto itemDto, Long userId) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(),
                userService.getUser(userId), fakeItemRequestRepository.getItemRequestByDescription(itemDto
                .getDescription()));
    }

    public ItemDto toItemDto(Item item) {
        List<Comment> comments = commentRepository.findByItemIdOrderByCreatedDesc(item.getId());
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment c : comments) {
            commentDtos.add(commentMapper.toCommentDto(c));
        }
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest(),
                null,
                null,
                commentDtos
        );
    }
}
