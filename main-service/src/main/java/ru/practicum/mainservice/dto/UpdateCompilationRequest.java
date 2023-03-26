package ru.practicum.mainservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    List<Long> events;
    Boolean pinned;
    String title;
}
