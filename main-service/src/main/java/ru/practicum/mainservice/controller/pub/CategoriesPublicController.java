package ru.practicum.mainservice.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoriesPublicController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get() {
        log.info("GET all users");
        return categoryService.getAll();
    }

    @GetMapping("/{catId}")
    public CategoryDto get(@Valid @NotNull @PathVariable Long catId) {
        log.info("GET all users");
        return categoryService.getById(catId);
    }

}
