package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.models.User;

import java.time.LocalDateTime;

@Data
public class EventShortDto {

    //    Краткое описание
    private String annotation;

    private Category category;

    //    Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;

    //    Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Long id;

    private User initiator;

    private Boolean paid;

    private String title;

    //    Количество просмотрев события
    private Long views;
}
