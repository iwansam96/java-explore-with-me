package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import ru.practicum.mainservice.models.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventFullDto {

    //    Краткое описание
    private String annotation;

    private Category category;

    //    Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;

    //    Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    //    Полное описание события
    private String description;

    //    Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Long id;

    private User initiator;

    private Location location;

    private Boolean paid;

    //    Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Long participantLimit;

    //    Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    //    Нужна ли пре-модерация заявок на участие
    private Boolean requestModeration;

    //    PENDING, PUBLISHED, CANCELED
    private EventState state;

    private String title;

    //    Количество просмотрев события
    private Long views;

    private List<CommentDto> comments;
}
