package ru.practicum.stats.server;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.stats.dto.EventInputDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static Event toEvent(EventInputDto eventInputDto) {
        Event event = new Event();
        event.setApp(eventInputDto.getApp());
        event.setUri(eventInputDto.getUri());
        event.setIp(eventInputDto.getIp());
        event.setEventCreated(eventInputDto.getEventCreated());
        return event;
    }
}
