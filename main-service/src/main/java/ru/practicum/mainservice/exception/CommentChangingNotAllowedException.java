package ru.practicum.mainservice.exception;

public class CommentChangingNotAllowedException extends RuntimeException {
    public CommentChangingNotAllowedException(String message) {
        super(message);
    }
}
