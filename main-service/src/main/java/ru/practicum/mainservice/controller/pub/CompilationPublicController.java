package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getShort() {
        log.info("GET /compilations");
        return compilationService.get();
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getFull(@Valid @NotNull @PathVariable Long compilationId) {
        log.info("GET /compilations/{}", compilationId);
        return compilationService.getById(compilationId);
    }
}
