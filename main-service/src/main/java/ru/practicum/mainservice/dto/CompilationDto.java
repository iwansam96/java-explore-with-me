package ru.practicum.mainservice.dto;

import lombok.Data;
import ru.practicum.mainservice.models.Event;

import java.util.List;

@Data
public class CompilationDto {
    private List<Event> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
