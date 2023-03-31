package ru.practicum.mainservice.controller.pub;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.models.EventSort;
import ru.practicum.mainservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getShort(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime rangeStart,
            @RequestParam(required = false) @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @Min(0) @RequestParam(defaultValue = "0") Integer from,
            @Min(0) @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        log.info("GET /events");
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        return eventService.getShort(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, ip, uri);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getFull(@Min(0) @NotNull @PathVariable Long eventId, HttpServletRequest request) {
        log.info("GET /events/{}", eventId);
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        return eventService.getById(eventId, ip, uri);
    }
}
