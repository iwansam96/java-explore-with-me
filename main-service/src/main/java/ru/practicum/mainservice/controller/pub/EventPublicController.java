package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.models.EventSort;
import ru.practicum.mainservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getShort(@RequestParam String text,
                                        @RequestParam List<Long> categories,
                                        @RequestParam Boolean paid,
                                        @RequestParam LocalDateTime rangeStart,
                                        @RequestParam LocalDateTime rangeEnd,
                                        @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                        @RequestParam EventSort sort,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        HttpServletRequest request) {
        log.info("GET /events");
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        return eventService.getShort(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, ip, uri);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getFull(@Valid @NotNull @PathVariable Long eventId, HttpServletRequest request) {
        log.info("GET /events/{}", eventId);
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        return eventService.getById(eventId, ip, uri);
    }
}
