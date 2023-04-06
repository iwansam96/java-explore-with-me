package ru.practicum.mainservice.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.CommentDto;
import ru.practicum.mainservice.dto.CommentDtoInput;
import ru.practicum.mainservice.models.Comment;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(CommentDtoInput commentDtoInput, User author, Event event) {
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setText(commentDtoInput.getText());
        comment.setEvent(event);
        return comment;
    }

    public static Comment toCommentForEdit(CommentDtoInput commentDtoInput, Comment oldComment) {
        Comment comment = new Comment();
        comment.setId(oldComment.getId());
        comment.setCreated(oldComment.getCreated());
        comment.setAuthor(oldComment.getAuthor());
        comment.setText(commentDtoInput.getText());
        comment.setEvent(oldComment.getEvent());
        return comment;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCreated(comment.getCreated());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setText(comment.getText());
        return commentDto;
    }
}
