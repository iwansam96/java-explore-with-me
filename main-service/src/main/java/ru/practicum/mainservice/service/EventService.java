package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.dto.mapper.EventMapper;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.models.*;
import ru.practicum.mainservice.repository.CategoryRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    //        выглядит жутко, но как сделать аккуратнее не придумалось
    public List<EventShortDto> getShort() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(event -> EventMapper.toEventShortDto(
                        event,
                        0L))
                .collect(Collectors.toList());
    }

    public List<EventShortDto> getShort(Long userId) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId);

        return events.stream()
                .map(event -> EventMapper.toEventShortDto(
                        event,
                        0L))
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getFull() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(event -> EventMapper.toEventFullDto(
                        event,
                        0L))
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getFull(Long userId) {
        List<Event> events = eventRepository.findAllByInitiator_Id(userId);

        return events.stream()
                .map(event -> EventMapper.toEventFullDto(
                        event,
                        0L))
                .collect(Collectors.toList());
    }


    public EventFullDto getById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        return EventMapper.toEventFullDto(
                event,
                0L
        );
    }

    public EventFullDto getById(Long userId, Long eventId) {
        Event event = eventRepository.findEventByIdAndInitiator_Id(eventId, userId);

        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        return EventMapper.toEventFullDto(
                event,
                0L
        );
    }


    public EventFullDto save(Long userId, NewEventDto newEventDto) {
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElse(null);
        if (category == null)
            throw new EntityNotFoundException("category " + newEventDto.getCategory() + " not found");

        User initiator = userRepository.findById(userId).orElse(null);
        if (initiator == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Event newEvent = EventMapper.toEvent(newEventDto, category, initiator);
        newEvent.setState(EventState.PENDING);

        Event savedEvent = eventRepository.save(newEvent);

        return EventMapper.toEventFullDto(savedEvent,  0L);
    }

    public EventFullDto update(Long eventId, UpdateEventAdminRequest updatedEvent) {
        Event eventToUpdate = eventRepository.findById(eventId).orElse(null);
        if (eventToUpdate == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        if (updatedEvent.getStateAction().equals(EventStateAction.PUBLISH_EVENT))
            eventToUpdate.setState(EventState.PUBLISHED);
        else if (updatedEvent.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
            eventToUpdate.setState(EventState.CANCELED);
        } else {
            throw new EntityNotFoundException("admin action " + updatedEvent.getStateAction() + " not found");
        }

        eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        eventToUpdate.setDescription(updatedEvent.getDescription());
        eventToUpdate.setEventDate(updatedEvent.getEventDate());
        eventToUpdate.setLocation(updatedEvent.getLocation());
        eventToUpdate.setPaid(updatedEvent.getPaid());
        eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        eventToUpdate.setTitle(updatedEvent.getTitle());

        Event savedEvent = eventRepository.save(eventToUpdate);

        return EventMapper.toEventFullDto(savedEvent, 0L);
    }

    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updatedEvent) {
        Event eventToUpdate = eventRepository.findEventByIdAndInitiator_Id(eventId, userId);
        if (eventToUpdate == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        if (updatedEvent.getStateAction().equals(EventStateAction.CANCEL_REVIEW))
            eventToUpdate.setState(EventState.CANCELED);
        else if (updatedEvent.getStateAction().equals(EventStateAction.SEND_TO_REVIEW)) {
            eventToUpdate.setState(EventState.PENDING);
        } else {
            throw new EntityNotFoundException("user action " + updatedEvent.getStateAction() + " not found");
        }

        eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        eventToUpdate.setDescription(updatedEvent.getDescription());
        eventToUpdate.setEventDate(updatedEvent.getEventDate());
        eventToUpdate.setLocation(updatedEvent.getLocation());
        eventToUpdate.setPaid(updatedEvent.getPaid());
        eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        eventToUpdate.setTitle(updatedEvent.getTitle());

        Event savedEvent = eventRepository.save(eventToUpdate);

        return EventMapper.toEventFullDto(savedEvent,  0L);
    }
}
