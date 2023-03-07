package ru.practicum.stats.server;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerRepository extends JpaRepository<Event, Long> {

//    not unique
//    with uris
    List<Event> findByEventCreatedBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

//    without uris
    List<Event> findByEventCreatedBetween(LocalDateTime start, LocalDateTime end);
}
