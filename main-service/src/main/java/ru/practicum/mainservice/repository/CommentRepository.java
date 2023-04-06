package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByIdAndEvent_Id(Long commentId, Long eventId);
}
