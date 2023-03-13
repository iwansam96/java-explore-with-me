package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.dto.mapper.ParticipationRequestMapper;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.models.Event;
import ru.practicum.mainservice.models.ParticipationRequest;
import ru.practicum.mainservice.models.ParticipationRequestStatus;
import ru.practicum.mainservice.models.User;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.ParticipationRequestRepository;
import ru.practicum.mainservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    //    for event creator
    public List<ParticipationRequestDto> get(Long eventId, Long userId) {
        List<ParticipationRequest> requests = participationRequestRepository.findAllByEvent_IdAndEvent_Initiator_Id(
                eventId, userId
        );

        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    public EventRequestStatusUpdateResult setRequestsStatus(
            EventRequestStatusUpdateRequest updateRequest, Long eventId, Long userId) {
        List<ParticipationRequest> requests =
                participationRequestRepository.findAllByIdInAndEventIdAndEvent_Initiator_Id(
                        updateRequest.getRequestIds(), eventId, userId);

        List<ParticipationRequest> updatedRequests = new ArrayList<>();
        for (var request : requests) {
            request.setStatus(updateRequest.getStatus());
            updatedRequests.add(request);
        }
        participationRequestRepository.saveAll(updatedRequests);

        List<ParticipationRequest> confirmedRequests = participationRequestRepository.
                findAllByStatusAndEvent_Initiator_Id(ParticipationRequestStatus.CONFIRMED, userId);
        List<ParticipationRequest> rejectedRequests = participationRequestRepository.
                findAllByStatusAndEvent_Initiator_Id(ParticipationRequestStatus.REJECTED, userId);

        return new EventRequestStatusUpdateResult(
                confirmedRequests.stream()
                        .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList()),
                rejectedRequests.stream()
                        .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList())
        );
    }

//    for participant
    public List<ParticipationRequestDto> get(Long userId) {
        List<ParticipationRequest> requests = participationRequestRepository.findAllByRequester_Id(userId);

        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    public ParticipationRequestDto save(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        ParticipationRequest newRequest = ParticipationRequestMapper.toParticipationRequest(user,
                ParticipationRequestStatus.PENDING, event);

        ParticipationRequest savedRequest = participationRequestRepository.save(newRequest);

        return ParticipationRequestMapper.toParticipationRequestDto(savedRequest);
    }

    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        ParticipationRequest request = participationRequestRepository.findById(requestId).orElse(null);
        if (request == null)
            throw new EntityNotFoundException("request " + requestId + " not found");

        request.setStatus(ParticipationRequestStatus.PENDING);

        ParticipationRequest canceledRequest = participationRequestRepository.save(request);

        return ParticipationRequestMapper.toParticipationRequestDto(canceledRequest);
    }
}
