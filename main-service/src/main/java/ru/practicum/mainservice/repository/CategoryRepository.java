package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
