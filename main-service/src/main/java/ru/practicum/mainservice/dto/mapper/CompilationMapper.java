package ru.practicum.mainservice.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.models.Compilation;
import ru.practicum.mainservice.models.Event;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setEvents(compilation.getEvents());
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        return compilationDto;
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setEvents(events);
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static Compilation mergeCompilations(UpdateCompilationRequest newCompilationDto,
                                                Compilation oldCompilation,
                                                List<Event> events) {
        if (newCompilationDto.getEvents() != null)
            oldCompilation.setEvents(events);
        if (newCompilationDto.getPinned() != null)
            oldCompilation.setPinned(newCompilationDto.getPinned());
        if (newCompilationDto.getTitle() != null && newCompilationDto.getTitle().isBlank())
            oldCompilation.setTitle(newCompilationDto.getTitle());
        return oldCompilation;
    }
}
