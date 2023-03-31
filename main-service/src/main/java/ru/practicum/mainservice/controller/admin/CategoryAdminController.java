package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.exception.CategoryIsInUseException;
import ru.practicum.mainservice.service.CategoryService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto save(@RequestBody NewCategoryDto categoryDto) {
        log.info("POST /admin/categories");
        return categoryService.save(categoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void delete(@Min(0) @NotNull @PathVariable Long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        try {
            categoryService.delete(catId);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryIsInUseException("category " + catId + " is in use");
        }
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@RequestBody NewCategoryDto categoryDto,
                              @Min(0) @NotNull @PathVariable Long catId) {
        log.info("PATCH /admin/categories/{}; new category: " + categoryDto, catId);
        return categoryService.update(categoryDto, catId);
    }
}
