package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PostMapping("/hit")
    public EventOutputDto hit(@RequestBody EventInputDto eventInputDto) {
        log.info("POST /hit");
        return service.hit(eventInputDto);
    }

    @GetMapping("/stats")
    public List<EventOutputDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam(required = false) List<String> uris,
                                        @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /event/, start=" + start + ", end=" + end + ", uris=" + uris + ", unique=" + unique);
        return service.getStats(start, end, uris, unique);
    }
}
