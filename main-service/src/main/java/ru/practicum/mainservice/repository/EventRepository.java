package ru.practicum.mainservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiator_IdInAndStateInAndCategory_IdInAndAndEventDateBetween(
            Pageable pageable,
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd
    );

    List<Event> findAllByInitiator_IdInAndStateInAndCategory_IdInAndAndEventDateBefore(
            Pageable pageable,
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeEnd
    );

    List<Event> findAllByInitiator_IdInAndStateInAndCategory_IdInAndAndEventDateAfter(
            Pageable pageable,
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart
    );

    List<Event> findAllByInitiator_IdInAndStateInAndCategory_IdIn(
            Pageable pageable,
            List<Long> users,
            List<EventState> states,
            List<Long> categories
    );

    List<Event> findAllByInitiator_IdInAndStateIn(
            Pageable pageable,
            List<Long> users,
            List<EventState> states
    );

    List<Event> findAllByInitiator_IdIn(Pageable pageable, List<Long> users);


    List<Event> findAllByStateIsAndEventDateBetweenAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategory_IdInAndPaidIs(Pageable pageable, EventState state, LocalDateTime rangeStart, LocalDateTime rangeEnd, String textAnnotation, String textDescription, List<Long> categories, Boolean paid);

    List<Event> findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategory_IdInAndPaidIs(Pageable pageable, EventState state, LocalDateTime rangeStart, String textAnnotation, String textDescription, List<Long> categories, Boolean paid);

    List<Event> findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndCategory_IdIn(Pageable pageable, EventState state, LocalDateTime rangeStart, String textAnnotation, String textDescription, List<Long> categories);

    List<Event> findAllByStateIsAndEventDateAfterAndAnnotationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Pageable pageable, EventState state, LocalDateTime rangeStart, String textAnnotation, String textDescription);

    List<Event> findAllByEventDateAfter(Pageable pageable, LocalDateTime rangeStart);

    List<Event> findAllByInitiator_Id(Pageable pageable, Long userId);

    Event findEventByIdAndInitiator_Id(Long eventId, Long userId);
}
