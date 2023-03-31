package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServerServiceImpl implements StatsServerService {

    private final StatsServerRepository repository;

    @Override
    @Transactional
    public Event hit(EventInputDto eventInputDto) {
        Event event = EventMapper.toEvent(eventInputDto);
        return repository.save(event);
    }

    @Override
    public List<EventOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<EventOutputDto> events;

        if (uris == null || uris.isEmpty())
            events = repository.getEvents(start, end);
        else
            events = repository.getEventsByUris(start, end, uris.stream().map(String::toLowerCase).collect(Collectors.toList()));

        events = events.stream().sorted(Comparator.comparingLong(EventOutputDto::getHits)).collect(Collectors.toList());
        events.sort(Collections.reverseOrder(Comparator.comparingLong(EventOutputDto::getHits)));
        return events;
    }
}
