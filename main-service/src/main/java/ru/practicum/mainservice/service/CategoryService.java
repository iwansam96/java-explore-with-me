package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.dto.mapper.CategoryMapper;
import ru.practicum.mainservice.exception.DublicateNameException;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.exception.IncorrectDataException;
import ru.practicum.mainservice.models.Category;
import ru.practicum.mainservice.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll(Integer from, Integer size) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        var categories = categoryRepository.findAll(pageRequest).toList();
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(Long id) {
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null)
            throw new EntityNotFoundException("category " + id + " not found");
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto save(NewCategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().isBlank())
            throw new IncorrectDataException("incorrect category data");

        var oldCategory = categoryRepository.findCategoryByNameLike(categoryDto.getName());
        if (oldCategory != null)
            throw new DublicateNameException("category "+ categoryDto.getName() + " is present");

        Category category = CategoryMapper.toCategory(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Transactional
    public CategoryDto update(NewCategoryDto categoryDto, Long catId) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().isBlank())
            throw new IncorrectDataException("incorrect category data");

        var oldCategory = categoryRepository.findCategoryByNameLike(categoryDto.getName());
        if (oldCategory != null && !Objects.equals(oldCategory.getId(), catId))
            throw new DublicateNameException("category "+ categoryDto.getName() + " is present");

        Category category = CategoryMapper.toCategory(categoryDto);
        category.setId(catId);
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Transactional
    public void delete(Long id) {
        Category categoryToDelete = categoryRepository.findById(id).orElse(null);
        if (categoryToDelete == null)
            throw new EntityNotFoundException("category " + id + " not found");

        categoryRepository.delete(categoryToDelete);
    }
}
