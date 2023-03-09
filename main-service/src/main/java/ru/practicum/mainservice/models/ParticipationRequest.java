package ru.practicum.mainservice.models;

import java.time.LocalDateTime;

public class ParticipationRequest {
    LocalDateTime created;
    Long event;
    Long id;
    Long requester;
    ParticipationRequestStatus status;
}
