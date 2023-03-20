package ru.practicum.mainservice.exception;

public class ParticipationRequestsAddNotAllowedException extends RuntimeException {
    public ParticipationRequestsAddNotAllowedException(String message) {
        super(message);
    }
}
