package ru.practicum.stats.server;

import ru.practicum.stats.dto.EventInputDto;

public class EventMapper {
    static Event toEvent(EventInputDto eventInputDto) {
        Event event = new Event();
        event.setApp(eventInputDto.getApp());
        event.setUri(eventInputDto.getUri());
        event.setIp(eventInputDto.getIp());
        event.setEventCreated(eventInputDto.getEventCreated());
        return event;
    }
}
