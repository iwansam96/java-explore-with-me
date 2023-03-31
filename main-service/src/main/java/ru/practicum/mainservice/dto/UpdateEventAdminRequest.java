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
    private String annotation;

    @NotNull
    private Long category;

    @NotNull
    private String description;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @NotNull
    private Boolean paid;

    @NotNull
    private Long participantLimit;

    @NotNull
    private Boolean requestModeration;

    @NotNull
    private EventStateAction stateAction;

    @NotNull
    private String title;
}
