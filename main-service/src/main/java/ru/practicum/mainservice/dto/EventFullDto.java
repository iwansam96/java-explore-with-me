package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.models.EventState;
import ru.practicum.mainservice.models.Location;
import ru.practicum.mainservice.models.User;

import java.time.LocalDateTime;

@Data
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
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    //    Нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    //    PENDING, PUBLISHED, CANCELED
    EventState state;

    String title;

    //    Количество просмотрев события
    Long views;
}
