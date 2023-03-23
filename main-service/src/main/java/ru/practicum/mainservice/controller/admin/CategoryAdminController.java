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
    public CategoryDto save(@RequestBody NewCategoryDto categoryDto) {
        log.info("POST /admin/categories");
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@Valid @NotNull @PathVariable Long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@RequestBody NewCategoryDto categoryDto,
                                         @Valid @NotNull @PathVariable Long catId) {
        log.info("PATCH /admin/categories/{}; new category: " + categoryDto, catId);
        return categoryService.update(categoryDto, catId);
    }
}
