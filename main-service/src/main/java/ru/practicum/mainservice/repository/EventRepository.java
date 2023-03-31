package ru.practicum.mainservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e " +
            "where e.initiator.id in ?1 " +
            "and e.state in ?2 " +
            "and e.category.id in ?3 " +
            "and e.eventDate between ?4 and ?5")
    List<Event> getByDatesBetween(Pageable pageable, List<Long> users, List<EventState> states, List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("select e from Event e " +
            "where e.initiator.id in ?1 " +
            "and e.state in ?2 " +
            "and e.category.id in ?3 " +
            "and e.eventDate < ?4")
    List<Event> getByDateBefore(Pageable pageable, List<Long> users, List<EventState> states, List<Long> categories,
                                  LocalDateTime rangeEnd);

    @Query("select e from Event e " +
            "where e.initiator.id in ?1 " +
            "and e.state in ?2 " +
            "and e.category.id in ?3 " +
            "and e.eventDate > ?4")
    List<Event> getByDateAfter(Pageable pageable, List<Long> users, List<EventState> states, List<Long> categories,
                                LocalDateTime rangeStart);


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


    @Query("select e from Event e " +
            "where e.state = ?1 " +
            "and e.eventDate between ?2 and ?3 " +
            "and lower(e.annotation) like concat('%', lower(?4) , '%') " +
            " or lower(e.description) like concat('%', lower(?5) , '%') " +
            "and e.category.id in ?6 " +
            "and e.paid = ?7")
    List<Event> getByRangeTextsCategoryAndPaid(Pageable pageable,
                                               EventState state,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               String textAnnotation,
                                               String textDescription,
                                               List<Long> categories,
                                               Boolean paid
    );

    @Query("select e from Event e " +
            "where e.state = ?1 " +
            "and e.eventDate > ?2 " +
            "and lower(e.annotation) like concat('%', lower(?3) , '%') " +
            " or lower(e.description) like concat('%', lower(?4) , '%') " +
            "and e.category.id in ?5 " +
            "and e.paid = ?6")
    List<Event> getByDateAfterTextsCategoryAndPaid(Pageable pageable,
                                               EventState state,
                                               LocalDateTime rangeStart,
                                               String textAnnotation,
                                               String textDescription,
                                               List<Long> categories,
                                               Boolean paid
    );

    @Query("select e from Event e " +
            "where e.state = ?1 " +
            "and e.eventDate > ?2 " +
            "and lower(e.annotation) like concat('%', lower(?3) , '%') " +
            " or lower(e.description) like concat('%', lower(?4) , '%') " +
            "and e.category.id in ?5")
    List<Event> getByDateAfterTextsCategory(Pageable pageable,
                                                   EventState state,
                                                   LocalDateTime rangeStart,
                                                   String textAnnotation,
                                                   String textDescription,
                                                   List<Long> categories
    );

    @Query("select e from Event e " +
            "where e.state = ?1 " +
            "and e.eventDate > ?2 " +
            "and lower(e.annotation) like concat('%', lower(?3) , '%') " +
            " or lower(e.description) like concat('%', lower(?4) , '%') ")
    List<Event> getByDateAfterTexts(Pageable pageable,
                                    EventState state,
                                    LocalDateTime rangeStart,
                                    String textAnnotation,
                                    String textDescription
    );


    List<Event> findAllByEventDateAfter(Pageable pageable, LocalDateTime rangeStart);

    List<Event> findAllByInitiator_Id(Pageable pageable, Long userId);

    Event findEventByIdAndInitiator_Id(Long eventId, Long userId);
}
