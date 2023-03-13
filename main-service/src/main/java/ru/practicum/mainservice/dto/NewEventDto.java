package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import ru.practicum.mainservice.models.Location;


import java.time.LocalDateTime;

@Data
public class NewEventDto {

    String annotation;

    Long category;

    String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Long participantLimit;

    Boolean requestModeration;

    String title;
}
