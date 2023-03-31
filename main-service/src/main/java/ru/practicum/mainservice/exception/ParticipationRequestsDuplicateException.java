package ru.practicum.mainservice.exception;

public class ParticipationRequestsDuplicateException extends RuntimeException {
    public ParticipationRequestsDuplicateException(String message) {
        super(message);
    }
}
