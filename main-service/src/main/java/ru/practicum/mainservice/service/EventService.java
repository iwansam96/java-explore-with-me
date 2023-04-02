package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.dto.mapper.EventMapper;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.exception.EventChangeNotAllowedException;
import ru.practicum.mainservice.exception.EventWithWrongStateException;
import ru.practicum.mainservice.exception.InvalidEventParametersException;
import ru.practicum.mainservice.models.*;
import ru.practicum.mainservice.repository.CategoryRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.UserRepository;
import ru.practicum.stats.client.EventClient;
import ru.practicum.stats.dto.EventInputDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final EventClient eventClient;

    public List<EventShortDto> getShort(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort sort, Integer from,
                                        Integer size, String ip, String uri) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        if (rangeStart == null)
            rangeStart = LocalDateTime.now();

        List<Event> events;
        if (rangeEnd != null && paid != null && categories != null && text != null)
            events = eventRepository.getByRangeTextsCategoryAndPaid(
                    pageRequest,
                    EventState.PUBLISHED,
                    rangeStart,
                    rangeEnd,
                    text,
                    text,
                    categories,
                    paid
            );
        else if (paid != null && categories != null && text != null)
            events = eventRepository.getByDateAfterTextsCategoryAndPaid(
                    pageRequest,
                    EventState.PUBLISHED,
                    rangeStart,
                    text,
                    text,
                    categories,
                    paid
            );
        else if (categories != null && text != null)
            events = eventRepository.getByDateAfterTextsCategory(
                    pageRequest,
                    EventState.PUBLISHED,
                    rangeStart,
                    text,
                    text,
                    categories
            );
        else if (text != null)
            events =
                eventRepository.getByDateAfterTexts(
                        pageRequest,
                        EventState.PUBLISHED,
                        rangeStart,
                        text,
                        text
                );
        else
            events = eventRepository.findAllByEventDateAfter(pageRequest, rangeStart);

        if (onlyAvailable)
            events = events.stream()
                    .filter(e -> e.getParticipantLimit() < e.getRequests().size())
                    .collect(Collectors.toList());


        List<EventShortDto> eventsDto = new ArrayList<>();
//        сохранение всех запрошенных событий в сервис статистики
        for (var event : events) {
            var stat = eventClient.getStats(event.getCreatedOn(), LocalDateTime.now(), List.of(uri), false);
            Long hits = 0L;
            if (stat != null && !stat.isEmpty()) {
                hits = stat.get(0).getHits();
            }
            eventsDto.add(EventMapper.toEventShortDto(event, hits));
        }

        if (sort != null && sort.equals(EventSort.EVENT_DATE))
            eventsDto = eventsDto.stream().sorted(Comparator.comparing(EventShortDto::getEventDate)).collect(Collectors.toList());
        else if (sort != null && sort.equals(EventSort.VIEWS))
            eventsDto = eventsDto.stream().sorted(Comparator.comparing(EventShortDto::getViews)).collect(Collectors.toList());

        eventClient.addStat(new EventInputDto("ewm-main", uri, ip));
        return eventsDto;
    }

    public EventFullDto getById(Long eventId, String ip, String uri) {
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event == null || !event.getState().equals(EventState.PUBLISHED))
            throw new EntityNotFoundException("event " + eventId + " not found");

        eventClient.addStat(new EventInputDto("ewm-main", uri, ip));

        return EventMapper.toEventFullDto(
                event,
                getEventHits(uri, event)
        );
    }




    public List<EventShortDto> getShort(Long userId, Integer from, Integer size, String uri) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Event> events = eventRepository.findAllByInitiator_Id(pageRequest, userId);

        return events.stream()
                .map(event -> EventMapper.toEventShortDto(
                        event,
                        getEventHits(uri, event)))
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getFull(List<Long> users, List<String> states, List<Long> categories,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size,
                                      String uri) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Event> events;
        if (users != null && states != null && categories != null && rangeStart != null && rangeEnd != null)
            events = eventRepository.getByDatesBetween(
                    pageRequest,
                    users,
                    states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                    categories,
                    rangeStart,
                    rangeEnd
            );
        else if (users != null && states != null && categories != null && rangeStart != null)
            events = eventRepository.getByDateAfter(
                    pageRequest,
                    users,
                    states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                    categories,
                    rangeStart
            );
        else if (users != null && states != null && categories != null && rangeEnd != null)
            events = eventRepository.getByDateBefore(
                    pageRequest,
                    users,
                    states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                    categories,
                    rangeEnd
            );
        else if (users != null && states != null && categories != null)
            events = eventRepository.findAllByInitiator_IdInAndStateInAndCategory_IdIn(
                    pageRequest,
                    users,
                    states.stream().map(EventState::valueOf).collect(Collectors.toList()),
                    categories
            );
        else if (users != null && states != null)
            events = eventRepository.findAllByInitiator_IdInAndStateIn(
                    pageRequest,
                    users,
                    states.stream().map(EventState::valueOf).collect(Collectors.toList())
            );
        else if (users != null)
            events = eventRepository.findAllByInitiator_IdIn(pageRequest, users);
        else
            events = eventRepository.findAll(pageRequest).toList();

        return events.stream()
                .map(event -> EventMapper.toEventFullDto(
                        event,
                        getEventHits(uri, event)))
                .collect(Collectors.toList());
    }

    public EventFullDto getById(Long userId, Long eventId, String uri) {
        Event event = eventRepository.findEventByIdAndInitiator_Id(eventId, userId);

        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        return EventMapper.toEventFullDto(
                event,
                getEventHits(uri, event)
        );
    }


    @Transactional
    public EventFullDto save(Long userId, NewEventDto newEventDto, String uri) {
        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate()))
            throw new InvalidEventParametersException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElse(null);
        if (category == null)
            throw new EntityNotFoundException("category " + newEventDto.getCategory() + " not found");

        User initiator = userRepository.findById(userId).orElse(null);
        if (initiator == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Event newEvent = EventMapper.toEvent(newEventDto, category, initiator);
        newEvent.setState(EventState.PENDING);

        Event savedEvent = eventRepository.save(newEvent);

        return EventMapper.toEventFullDto(savedEvent,  getEventHits(uri, savedEvent));
    }

    @Transactional
    public EventFullDto update(Long eventId, UpdateEventAdminRequest updatedEvent, String uri) {
        Event eventToUpdate = eventRepository.findById(eventId).orElse(null);
        if (eventToUpdate == null) {
            throw new EntityNotFoundException("event " + eventId + " not found");
        }
        if (updatedEvent == null)
            return EventMapper.toEventFullDto(eventToUpdate, getEventHits(uri, eventToUpdate));

//        изменение времени события на некорректное
        if (updatedEvent.getEventDate() != null && LocalDateTime.now().plusHours(1).isAfter(updatedEvent.getEventDate())) {
            throw new InvalidEventParametersException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через час от текущего момента");
        }

//        публикация опубликованного события
        if (EventState.PUBLISHED.equals(eventToUpdate.getState()) &&
                EventStateAction.PUBLISH_EVENT.equals(updatedEvent.getStateAction())) {
            throw new InvalidEventParametersException("event is already published");
        }

//        публикация отмененного события
        if (EventState.CANCELED.equals(eventToUpdate.getState()) &&
                EventStateAction.PUBLISH_EVENT.equals(updatedEvent.getStateAction())) {
            throw new InvalidEventParametersException("event is already canceled");
        }

//        отмена опубликованного события
        if (EventState.PUBLISHED.equals(eventToUpdate.getState()) &&
                EventStateAction.REJECT_EVENT.equals(updatedEvent.getStateAction())) {
            throw new InvalidEventParametersException("cannot cancel already published event");
        }



        if (updatedEvent.getStateAction().equals(EventStateAction.PUBLISH_EVENT)) {
            eventToUpdate.setState(EventState.PUBLISHED);
        }
        else if (updatedEvent.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
            eventToUpdate.setState(EventState.CANCELED);
        } else {
            throw new EventWithWrongStateException("event is not in the right state");
        }

        if (updatedEvent.getAnnotation() != null)
            eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        if (updatedEvent.getDescription() != null)
            eventToUpdate.setDescription(updatedEvent.getDescription());
        if (updatedEvent.getEventDate() != null)
            eventToUpdate.setEventDate(updatedEvent.getEventDate());
        if (updatedEvent.getLocation() != null)
            eventToUpdate.setLocation(updatedEvent.getLocation());
        if (updatedEvent.getPaid() != null)
            eventToUpdate.setPaid(updatedEvent.getPaid());
        if (updatedEvent.getParticipantLimit() != null)
            eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        if (updatedEvent.getRequestModeration() != null)
            eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        if (updatedEvent.getTitle() != null)
            eventToUpdate.setTitle(updatedEvent.getTitle());

        Event savedEvent = eventRepository.save(eventToUpdate);

        return EventMapper.toEventFullDto(savedEvent, getEventHits(uri, savedEvent));
    }

    @Transactional
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updatedEvent, String uri) {
        Event eventToUpdate = eventRepository.findEventByIdAndInitiator_Id(eventId, userId);
        if (eventToUpdate == null)
            throw new EntityNotFoundException("event " + eventId + " not found");
        if (updatedEvent == null)
            return EventMapper.toEventFullDto(eventToUpdate, getEventHits(uri, eventToUpdate));

        if (updatedEvent.getEventDate() != null && LocalDateTime.now().minusHours(2).isAfter(updatedEvent.getEventDate())) {
            throw new InvalidEventParametersException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }


        boolean isEventCanceled = EventState.CANCELED.equals(eventToUpdate.getState());
        boolean isEventPending = EventState.PENDING.equals(eventToUpdate.getState());
        if ( !(isEventCanceled || isEventPending) )
            throw new EventChangeNotAllowedException("Only pending or canceled events can be changed");

        if (EventStateAction.CANCEL_REVIEW.equals(updatedEvent.getStateAction()))
            eventToUpdate.setState(EventState.CANCELED);
        else if (EventStateAction.SEND_TO_REVIEW.equals(updatedEvent.getStateAction())) {
            eventToUpdate.setState(EventState.PENDING);
        } else {
            throw new EventWithWrongStateException("event is not in the right state");
        }

        if (updatedEvent.getAnnotation() != null)
            eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        if (updatedEvent.getDescription() != null)
            eventToUpdate.setDescription(updatedEvent.getDescription());
        if (updatedEvent.getEventDate() != null)
            eventToUpdate.setEventDate(updatedEvent.getEventDate());
        if (updatedEvent.getLocation() != null)
            eventToUpdate.setLocation(updatedEvent.getLocation());
        if (updatedEvent.getPaid() != null)
            eventToUpdate.setPaid(updatedEvent.getPaid());
        if (updatedEvent.getParticipantLimit() != null)
            eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        if (updatedEvent.getRequestModeration() != null)
            eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        if (updatedEvent.getTitle() != null)
            eventToUpdate.setTitle(updatedEvent.getTitle());

        Event savedEvent = eventRepository.save(eventToUpdate);

        return EventMapper.toEventFullDto(savedEvent,  getEventHits(uri, savedEvent));
    }


    private Long getEventHits(String uri, Event event) {
        var stat = eventClient.getStats(event.getCreatedOn(), LocalDateTime.now(), List.of(uri), false);
        Long hits = 0L;
        if (stat != null && !stat.isEmpty()) {
            hits = stat.get(0).getHits();
        }
        return hits;
    }
}
