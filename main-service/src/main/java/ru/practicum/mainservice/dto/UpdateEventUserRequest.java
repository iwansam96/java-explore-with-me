package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.EventStateAction;
import ru.practicum.mainservice.models.Location;

import java.time.LocalDateTime;

public class UpdateEventUserRequest {
    String annotation;
    Long category;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    EventStateAction stateAction;
    String title;
}
