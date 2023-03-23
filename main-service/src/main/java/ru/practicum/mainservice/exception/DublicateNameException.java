package ru.practicum.mainservice.exception;

public class DublicateNameException extends RuntimeException {
    public DublicateNameException(String message) {
        super(message);
    }
}
