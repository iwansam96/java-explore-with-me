package ru.practicum.mainservice.dto;

import ru.practicum.mainservice.models.ParticipationRequestStatus;

import java.util.List;

public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    ParticipationRequestStatus status;
}
