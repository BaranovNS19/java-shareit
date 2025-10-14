package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public Comment toComment(CommentDto commentDto, Item item, User author) {
        return new Comment(commentDto.getId(), commentDto.getText(), item, author, LocalDateTime.now());
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getUser().getName(), comment.getCreated());
    }
}
