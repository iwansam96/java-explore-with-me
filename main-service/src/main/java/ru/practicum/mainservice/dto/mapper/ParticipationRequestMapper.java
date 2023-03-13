package ru.practicum.mainservice.dto.mapper;

import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.ParticipationRequest;
import ru.practicum.mainservice.models.ParticipationRequestStatus;
import ru.practicum.mainservice.models.User;

public class ParticipationRequestMapper {

//    public static ParticipationRequest toParticipationRequest() {
//
//    }

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        ParticipationRequestDto requestDto = new ParticipationRequestDto();
        requestDto.setCreated(request.getCreated());
        requestDto.setEvent(request.getId());
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
