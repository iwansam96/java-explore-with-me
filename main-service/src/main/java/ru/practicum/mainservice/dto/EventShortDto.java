package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.models.User;

import java.time.LocalDateTime;

public class EventShortDto {

    //    Краткое описание
    String annotation;

    Category category;

    //    Количество одобренных заявок на участие в данном событии
    Long confirmedRequests;

    //    Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Long id;

    User initiator;

    Boolean paid;

    String title;

    //    Количество просмотрев события
    Long views;
}
