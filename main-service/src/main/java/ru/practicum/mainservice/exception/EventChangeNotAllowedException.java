package ru.practicum.mainservice.exception;

public class EventChangeNotAllowedException extends RuntimeException {
    public EventChangeNotAllowedException(String message) {
        super(message);
    }
}
