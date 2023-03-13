package ru.practicum.mainservice.dto;

import lombok.Data;
import ru.practicum.mainservice.models.Event;

import java.util.List;

@Data
public class CompilationDto {
    List<Event> events;
    Long id;
    Boolean pinned;
    String title;
}
