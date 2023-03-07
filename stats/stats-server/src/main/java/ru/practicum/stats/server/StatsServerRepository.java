package ru.practicum.stats.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerRepository extends JpaRepository<Event, Long> {
    @Query("select new ru.practicum.stats.dto.EventOutputDto(count(e.uri), e.app, e.uri) from Event e where e.eventCreated between ?1 and ?2 " +
            "group by e.app, e.uri")
    List<EventOutputDto> getEvents(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.stats.dto.EventOutputDto(count(e.uri), e.app, e.uri) from Event e where e.eventCreated between ?1 and ?2 " +
            "and e.uri in ?3 group by e.app, e.uri")
    List<EventOutputDto> getEventsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
