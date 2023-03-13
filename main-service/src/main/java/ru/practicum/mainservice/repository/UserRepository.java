package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
