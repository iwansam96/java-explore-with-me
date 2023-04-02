package ru.practicum.mainservice.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CommentDto;
import ru.practicum.mainservice.dto.CommentDtoInput;
import ru.practicum.mainservice.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentPrivateController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto add(@Min(0) @NotNull @PathVariable Long userId,
                          @Min(0) @NotNull @PathVariable Long eventId,
                          @Valid @NotNull @RequestBody CommentDtoInput commentDtoInput) {
        log.info("POST /users/" + userId + "/events/" + eventId + "/comment");

        return commentService.add(userId, eventId, commentDtoInput);
    }

    @PatchMapping("/{commentId}")
    public CommentDto edit(@Min(0) @NotNull @PathVariable Long userId,
                        @Min(0) @NotNull @PathVariable Long eventId,
                        @Min(0) @NotNull @PathVariable Long commentId,
                        @Valid @NotNull @RequestBody CommentDtoInput commentDtoInput) {
        log.info("PATCH /users/" + userId + "/events/" + eventId + "/comment");

        return commentService.edit(userId, eventId, commentId, commentDtoInput);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void delete(@Min(0) @NotNull @PathVariable Long userId,
                       @Min(0) @NotNull @PathVariable Long eventId,
                       @Min(0) @NotNull @PathVariable Long commentId) {
        log.info("DELETE /users/" + userId + "/events/" + eventId + "/comment/" + commentId);

        commentService.delete(userId, eventId, commentId);
    }
}
