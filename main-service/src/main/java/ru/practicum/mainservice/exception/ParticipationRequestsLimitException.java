package ru.practicum.mainservice.exception;

public class ParticipationRequestsLimitException extends RuntimeException {
    public ParticipationRequestsLimitException(String message) {
        super(message);
    }
}
