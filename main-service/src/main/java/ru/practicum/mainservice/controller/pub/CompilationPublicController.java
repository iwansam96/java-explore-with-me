package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.service.CompilationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getShort(@RequestParam(required = false) Boolean pinned,
                                         @Min(0) @RequestParam(defaultValue = "0") Integer from,
                                         @Min(0) @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /compilations");
        return compilationService.get(pinned, from, size);
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getFull(@Min(0) @NotNull @PathVariable Long compilationId) {
        log.info("GET /compilations/{}", compilationId);
        return compilationService.getById(compilationId);
    }
}
