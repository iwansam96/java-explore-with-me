package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.models.EventState;
import ru.practicum.mainservice.models.Location;
import ru.practicum.mainservice.models.User;

import java.time.LocalDateTime;

public class EventFullDto {

    //    Краткое описание
    String annotation;

    Category category;

    //    Количество одобренных заявок на участие в данном событии
    Long confirmedRequests;

    //    Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    //    Полное описание события
    String description;

    //    Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Long id;

    User initiator;

    Location location;

    Boolean paid;

    //    Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Long participantLimit;

    //    Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    //    Нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    //    PENDING, PUBLISHED, CANCELED
    EventState state;

    String title;

    //    Количество просмотрев события
    Long views;
}
