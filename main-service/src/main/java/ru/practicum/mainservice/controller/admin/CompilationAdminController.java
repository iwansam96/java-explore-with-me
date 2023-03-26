package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto save(@Valid @NotNull @RequestBody NewCompilationDto compilationDto) {
        log.info("POST /admin/compilations");
        return compilationService.save(compilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{compilationId}")
    public void delete(@Valid @NotNull @PathVariable Long compilationId) {
        log.info("DELETE /admin/compilations/{}", compilationId);
        compilationService.delete(compilationId);
    }

    @PatchMapping("/{compilationId}")
    public CompilationDto update(@Valid @NotNull @RequestBody UpdateCompilationRequest compilationDto,
                              @Valid @NotNull @PathVariable Long compilationId) {
        log.info("PATCH /admin/compilations/{}", compilationId);
        return compilationService.update(compilationDto, compilationId);
    }
}
