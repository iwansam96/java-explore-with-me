package ru.practicum.stats.server;

import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

public class EventMapper {
    static Event toEvent(EventInputDto eventInputDto) {
        Event event = new Event();
        event.setApp(eventInputDto.getApp());
        event.setUri(eventInputDto.getUri());
        event.setIp(eventInputDto.getIp());
        event.setEventCreated(eventInputDto.getEventCreated());
        return event;
    }

    static EventOutputDto toEventOutputDto(Event event) {
        EventOutputDto eventInputDto = new EventOutputDto();
        eventInputDto.setApp(event.getApp());
        eventInputDto.setUri(event.getUri());
//        eventInputDto.setIp(event.getIp());
        eventInputDto.setEventCreated(event.getEventCreated());
        return eventInputDto;
    }
}
