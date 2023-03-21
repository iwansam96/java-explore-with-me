package ru.practicum.stats.server;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class StatsServerController {

    private final StatsServerService service;

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss";

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public Event hit(@RequestBody EventInputDto eventInputDto) {
        log.info("POST /hit");
        return service.hit(eventInputDto);
    }

    @GetMapping("/stats")
    public List<EventOutputDto> getStats(@RequestParam @JsonSerialize(using = LocalDateTimeSerializer.class)
                                             @DateTimeFormat(pattern = datePattern) LocalDateTime start,
                                        @RequestParam @JsonSerialize(using = LocalDateTimeSerializer.class)
                                             @DateTimeFormat(pattern = datePattern) LocalDateTime end,
                                        @RequestParam(required = false) List<String> uris,
                                        @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /event/, start=" + start + ", end=" + end + ", uris=" + uris + ", unique=" + unique);
        return service.getStats(start, end, uris, unique);
    }
}
