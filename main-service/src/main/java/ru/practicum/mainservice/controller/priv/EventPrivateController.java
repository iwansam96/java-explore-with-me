package ru.practicum.mainservice.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.ParticipationRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {
    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;

    @GetMapping
    public List<EventShortDto> getFull(@Min(0) @NotNull @PathVariable Long userId,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       HttpServletRequest request) {
        log.info("GET /users/{}/events", userId);
        String uri = request.getRequestURI();
        return eventService.getShort(userId, from, size, uri);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventFullDto save(@Min(0) @NotNull @PathVariable Long userId,
                             @Valid @NotNull @RequestBody NewEventDto newEventDto,
                             HttpServletRequest request) {
        log.info("POST /users/{}/events", userId);
        String uri = request.getRequestURI();
        return eventService.save(userId, newEventDto, uri);
    }


    @GetMapping("/{eventId}")
    public EventFullDto getFull(@Min(0) @NotNull @PathVariable Long userId,
                                @Min(0) @NotNull @PathVariable Long eventId,
                                HttpServletRequest request) {
        log.info("GET /users/" + userId + "/events/" + eventId);
        String uri = request.getRequestURI();
        return eventService.getById(userId, eventId, uri);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Min(0) @NotNull @PathVariable Long userId,
                               @Min(0) @NotNull @PathVariable Long eventId,
                               @Valid @NotNull @RequestBody UpdateEventUserRequest updateEventUserRequest,
                               HttpServletRequest request) {
        log.info("PATCH /users/" + userId + "/events/" + eventId);
        String uri = request.getRequestURI();
        return eventService.update(userId, eventId, updateEventUserRequest, uri);
    }


    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getFullRequests(@Min(0) @NotNull @PathVariable Long userId,
                                                         @Min(0) @NotNull @PathVariable Long eventId) {
        log.info("GET /users/" + userId + "/events/" + eventId + "/requests");
        return participationRequestService.get(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult setStatuses(@Min(0) @NotNull @PathVariable Long userId,
                                                      @Min(0) @NotNull @PathVariable Long eventId,
                                                      @Valid @NotNull @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        log.info("PATCH /users/" + userId + "/events/" + eventId + "/requests");
        return participationRequestService.setRequestsStatus(updateRequest, eventId, userId);
    }
}
