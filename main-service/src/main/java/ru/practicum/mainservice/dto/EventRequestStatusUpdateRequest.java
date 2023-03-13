package ru.practicum.mainservice.dto;

import lombok.Data;
import ru.practicum.mainservice.models.ParticipationRequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    ParticipationRequestStatus status;
}
