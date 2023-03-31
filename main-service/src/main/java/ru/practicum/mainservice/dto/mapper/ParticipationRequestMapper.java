package ru.practicum.mainservice.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.ParticipationRequest;
import ru.practicum.mainservice.models.ParticipationRequestStatus;
import ru.practicum.mainservice.models.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationRequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        ParticipationRequestDto requestDto = new ParticipationRequestDto();
        requestDto.setCreated(request.getCreated());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setId(request.getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }

    public static ParticipationRequest toParticipationRequest(User requester,
                                                              ParticipationRequestStatus status,
                                                              Event event) {
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requester);
        request.setStatus(status);
        request.setEvent(event);
        return request;
    }
}
