package ru.practicum.mainservice.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.service.ParticipationRequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {
    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;

    @GetMapping
    public List<EventShortDto> getFull(@Valid @NotNull @PathVariable Long userId,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /users/{}/events", userId);
        return eventService.getShort(userId, from, size);
    }

    @PostMapping
    public EventFullDto save(@Valid @NotNull @PathVariable Long userId,
                             @Valid @NotNull @RequestBody NewEventDto newEventDto) {
        log.info("POST /users/{}/events", userId);
        return eventService.save(userId, newEventDto);
    }


    @GetMapping("/{eventId}")
    public EventFullDto getFull(@Valid @NotNull @PathVariable Long userId,
                                @Valid @NotNull @PathVariable Long eventId) {
        log.info("GET /users/" + userId + "/events/" + eventId);
        return eventService.getById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Valid @NotNull @PathVariable Long userId,
                               @Valid @NotNull @PathVariable Long eventId,
                               @Valid @NotNull @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("PATCH /users/" + userId + "/events/" + eventId);
        return eventService.update(userId, eventId, updateEventUserRequest);
    }


    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getFullRequests(@Valid @NotNull @PathVariable Long userId,
                                                         @Valid @NotNull @PathVariable Long eventId) {
        log.info("GET /users/" + userId + "/events/" + eventId + "/requests");
        return participationRequestService.get(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult setStatuses(@Valid @NotNull @PathVariable Long userId,
                                                      @Valid @NotNull @PathVariable Long eventId,
                                                      @Valid @NotNull @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        log.info("PATCH /users/" + userId + "/events/" + eventId + "/requests");
        return participationRequestService.setRequestsStatus(updateRequest, eventId, userId);
    }
}
