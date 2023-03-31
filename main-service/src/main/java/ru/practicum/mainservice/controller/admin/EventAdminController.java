package ru.practicum.mainservice.controller.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getFull(@RequestParam(required = false) List<Long> users,
                                      @RequestParam(required = false) List<String> states,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false) @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime rangeStart,
                                      @RequestParam(required = false) @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime rangeEnd,
                                      @Min(0) @RequestParam(defaultValue = "0") Integer from,
                                      @Min(0) @RequestParam(defaultValue = "10") Integer size,
                                      HttpServletRequest request) {
        log.info("GET /admin/events");
        String uri = request.getRequestURI();
        return eventService.getFull(users, states, categories, rangeStart, rangeEnd, from, size, uri);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Min(0) @NotNull @PathVariable Long eventId,
                                @RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                               HttpServletRequest request) {
        log.info("PATCH /admin/events/{}", eventId);
        String uri = request.getRequestURI();
        return eventService.update(eventId, updateEventAdminRequest, uri);
    }
}
