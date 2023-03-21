package ru.practicum.mainservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import ru.practicum.mainservice.models.EventStateAction;
import ru.practicum.mainservice.models.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {
    @NotNull
    String annotation;

    @NotNull
    Long category;

    @NotNull
    String description;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @NotNull
    Location location;

    @NotNull
    Boolean paid;

    @NotNull
    Long participantLimit;

    @NotNull
    Boolean requestModeration;

    @NotNull
    EventStateAction stateAction;

    @NotNull
    String title;
}
