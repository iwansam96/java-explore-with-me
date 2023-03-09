package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.Event;

import java.util.List;

public class CompilationDto {
    List<Event> events;
    Long id;
    Boolean pinned;
    String title;
}
