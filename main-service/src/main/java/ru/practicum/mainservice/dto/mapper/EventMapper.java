package ru.practicum.mainservice.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.ParticipationRequestStatus;
import ru.practicum.mainservice.models.User;
import ru.practicum.stats.dto.EventInputDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static EventInputDto toEventInputDto(EventShortDto eventShortDto, String ip, String uri, String app) {
        EventInputDto eventInputDto = new EventInputDto();
        eventInputDto.setEventCreated(LocalDateTime.now());
        eventInputDto.setApp(app);
        eventInputDto.setIp(ip);
        eventInputDto.setUri(uri);
        return eventInputDto;
    }

    public static EventShortDto toEventShortDto(Event event, Long views) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(event.getCategory());
        eventShortDto.setConfirmedRequests(
                event.getRequests().stream()
                .filter(r->r.getStatus().equals(ParticipationRequestStatus.CONFIRMED))
                .count()
        );
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(event.getInitiator());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(views);
        return eventShortDto;
    }

    public static EventFullDto toEventFullDto(Event event, Long views) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(event.getCategory());
        var requests = event.getRequests();
        if (requests == null)
            eventFullDto.setConfirmedRequests(null);
        else
            eventFullDto.setConfirmedRequests((long) requests.size());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setId(event.getId());
        eventFullDto.setInitiator(event.getInitiator());
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(views);
        if (event.getComments() != null)
            eventFullDto.setComments(event.getComments().stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList())
            );
        return eventFullDto;
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User initiator) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setInitiator(initiator);
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static Event toEvent(UpdateEventAdminRequest newEventDto, Category category) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static Event toEvent(UpdateEventUserRequest newEventDto, Category category) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }
}
