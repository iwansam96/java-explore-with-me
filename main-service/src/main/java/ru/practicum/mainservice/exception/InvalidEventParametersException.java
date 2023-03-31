package ru.practicum.mainservice.exception;

public class InvalidEventParametersException extends RuntimeException {
    public InvalidEventParametersException(String message) {
        super(message);
    }
}
