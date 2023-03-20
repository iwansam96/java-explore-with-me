package ru.practicum.mainservice.exception;

public class EventWithWrongStateException extends RuntimeException {
    public EventWithWrongStateException(String message) {
        super(message);
    }
}
