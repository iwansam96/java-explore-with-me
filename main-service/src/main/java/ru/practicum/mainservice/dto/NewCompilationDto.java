package ru.practicum.mainservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewCompilationDto {
    @NotNull
    List<Long> events;

    @NotNull
    Boolean pinned;

    @NotBlank
    String title;
}
