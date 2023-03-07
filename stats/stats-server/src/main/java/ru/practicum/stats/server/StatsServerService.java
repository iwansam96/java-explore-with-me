package ru.practicum.stats.server;

import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerService {
    Event hit(EventInputDto eventInputDto);

    List<EventOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
