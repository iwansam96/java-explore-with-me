package ru.practicum.mainservice.dto;

import java.util.List;

public class UpdateCompilationRequest {
    List<Long> events;
    Boolean pinned;
    String title;
}
