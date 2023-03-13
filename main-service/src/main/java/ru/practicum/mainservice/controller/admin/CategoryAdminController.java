package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto save(@Valid @NotNull @RequestBody NewCategoryDto categoryDto) {
        log.info("POST user");
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@Valid @NotNull @PathVariable Long catId) {
        log.info("DELETE user {}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@Valid @NotNull @RequestBody NewCategoryDto categoryDto,
                                         @Valid @NotNull @PathVariable Long catId) {
        log.info("PATCH user {}", catId);
        return categoryService.save(categoryDto, catId);
    }
}
