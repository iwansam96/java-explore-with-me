package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.CommentDto;
import ru.practicum.mainservice.dto.CommentDtoInput;
import ru.practicum.mainservice.dto.mapper.CommentMapper;
import ru.practicum.mainservice.exception.CommentChangingNotAllowedException;
import ru.practicum.mainservice.exception.CommentNotAllowedException;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.models.Comment;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.User;
import ru.practicum.mainservice.repository.CommentRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

//    Private
    public CommentDto add(Long userId, Long eventId, CommentDtoInput commentDtoInput) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

//        комментатор - инициатор события
        boolean isOwner = event.getInitiator().getId().equals(userId);
//        событие прошло
        boolean isEventPassed = event.getEventDate().isBefore(LocalDateTime.now());
//        комментатор - участник события
        var request = user.getRequests().stream()
                .filter(r -> r.getEvent().equals(event))
                .findFirst().orElse(null);
        boolean isCommentatorParticipant = false;
        if (request != null)
            isCommentatorParticipant = event.getRequests().contains(request);
        if (isOwner)
            throw new CommentNotAllowedException("user " + userId + " cannot add comment to event " + eventId + ": user is owner");
        if (!isCommentatorParticipant)
            throw new CommentNotAllowedException("user " + userId + " cannot add comment to event " + eventId + ": user aren't participant");

        Comment newComment = commentRepository.save(CommentMapper.toComment(commentDtoInput, user, event));

        return CommentMapper.toCommentDto(newComment);
    }

    public CommentDto edit(Long userId, Long eventId, Long commentId, CommentDtoInput commentDtoInput) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Comment oldComment = commentRepository.findByIdAndEvent_Id(commentId, eventId);
        if (oldComment == null)
            throw new EntityNotFoundException("comment " + commentId + " from user " + userId + " to event " + eventId + " not found");

        if (!oldComment.getAuthor().equals(user))
            throw new CommentChangingNotAllowedException("comment " + commentId + " changing not allowed for user " + userId);

        Comment newComment = commentRepository.save(CommentMapper.toCommentForEdit(commentDtoInput, oldComment));

        return CommentMapper.toCommentDto(newComment);
    }

    public void delete(Long userId, Long eventId, Long commentId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Comment comment = commentRepository.findByIdAndEvent_Id(commentId, eventId);
        if (comment == null)
            throw new EntityNotFoundException("comment " + commentId + " from user " + userId + " to event " + eventId + " not found");

        if (!comment.getAuthor().equals(user))
            throw new CommentChangingNotAllowedException("comment " + commentId + " changing not allowed for user " + userId);

        commentRepository.deleteById(commentId);
    }


//    Admin
    public CommentDto edit(Long eventId, Long commentId, CommentDtoInput commentDtoInput) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        Comment oldComment = commentRepository.findByIdAndEvent_Id(commentId, eventId);
        if (oldComment == null)
            throw new EntityNotFoundException("comment " + commentId + " to event " + eventId + " not found");

        Comment newComment = commentRepository.save(CommentMapper.toCommentForEdit(commentDtoInput, oldComment));

        return CommentMapper.toCommentDto(newComment);
    }

    public void delete(Long eventId, Long commentId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        Comment comment = commentRepository.findByIdAndEvent_Id(commentId, eventId);
        if (comment == null)
            throw new EntityNotFoundException("comment " + commentId + " to event " + eventId + " not found");

        commentRepository.deleteById(commentId);
    }
}
