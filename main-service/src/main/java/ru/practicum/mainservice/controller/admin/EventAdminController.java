package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getFull() {
        log.info("GET /admin/events");
        return eventService.getFull();
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Valid @NotNull @PathVariable Long eventId,
                                @Valid @NotNull @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("PATCH /admin/events/{}", eventId);
        return eventService.update(eventId, updateEventAdminRequest);
    }
}
