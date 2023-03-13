package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
