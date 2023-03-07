package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServerServiceImpl implements StatsServerService {

    private final StatsServerRepository repository;

    @Override
    public EventOutputDto hit(EventInputDto eventInputDto) {
        Event event = EventMapper.toEvent(eventInputDto);
        Event newEvent = repository.save(event);
        return EventMapper.toEventOutputDto(newEvent);
    }

    @Override
    public List<EventOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<Event> events;

        if (uris == null || uris.isEmpty())
            events = repository.findByEventCreatedBetween(start, end);
        else
            events = repository.findByEventCreatedBetweenAndUriIn(start, end, uris);

        var ipSet = new HashSet<>();
        if (unique)
            return events.stream().filter(e -> ipSet.add(e.getIp())).map(EventMapper::toEventOutputDto)
                    .collect(Collectors.toList());
        return events.stream().map(EventMapper::toEventOutputDto).collect(Collectors.toList());
    }
}
