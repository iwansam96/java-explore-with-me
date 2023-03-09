package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.ParticipationRequestStatus;

import java.time.LocalDateTime;

public class ParticipationRequestDto {
    LocalDateTime created;
    Long event;
    Long id;
    Long requester;
    ParticipationRequestStatus status;
}
