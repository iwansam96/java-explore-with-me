package ru.practicum.mainservice.exception;

public class CategoryIsInUseException extends RuntimeException {
    public CategoryIsInUseException(String message) {
        super(message);
    }
}
