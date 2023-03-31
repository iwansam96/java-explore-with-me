package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoriesPublicController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get(@Min(0) @RequestParam(defaultValue = "0") Integer from,
                                 @Min(0) @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET all users");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto get(@Min(0) @NotNull @PathVariable Long catId) {
        log.info("GET all users");
        return categoryService.getById(catId);
    }

}
