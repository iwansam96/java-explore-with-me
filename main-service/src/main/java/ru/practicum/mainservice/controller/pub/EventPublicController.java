package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getShort() {
        log.info("GET /events");
        return eventService.getShort();
    }

    @GetMapping("/{eventId}")
    public EventFullDto getFull(@Valid @NotNull @PathVariable Long eventId) {
        log.info("GET /events/{}", eventId);
        return eventService.getById(eventId);
    }
}
