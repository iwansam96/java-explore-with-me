package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.dto.ParticipationRequestDto;
import ru.practicum.mainservice.dto.mapper.ParticipationRequestMapper;
import ru.practicum.mainservice.exception.*;
import ru.practicum.mainservice.models.*;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.ParticipationRequestRepository;
import ru.practicum.mainservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new EntityNotFoundException("user " + userId + " not found");

        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null)
            throw new EntityNotFoundException("event " + eventId + " not found");

        var requests = event.getRequests();

        Long acceptedRequestsNumber = requests.stream()
                .filter(request -> request.getStatus().equals(ParticipationRequestStatus.CONFIRMED))
                .count();

        if (acceptedRequestsNumber.equals(event.getParticipantLimit())) {
            throw new ParticipationRequestsLimitException("The participant limit has been reached");
        }



        List<ParticipationRequest> updatedRequests = new ArrayList<>();
//        обход всех заявок для которых нужно обновить статус
        for (var request : requests.stream()
                .filter(r -> updateRequest.getRequestIds().contains(r.getId())).collect(Collectors.toList())) {
            if (!request.getStatus().equals(ParticipationRequestStatus.PENDING)) {
                throw new ParticipationRequestsChangeStatusException("request must have status PENDING");
            }
//            если лимит заявок не достигнут, устанавливаем переданный статус
            if (event.getParticipantLimit() > acceptedRequestsNumber) {
                request.setStatus(updateRequest.getStatus());
                updatedRequests.add(request);
                acceptedRequestsNumber++;
//                иначе отклоняем все остальные переданные на вход зявки
            } else {
                throw new ParticipationRequestsLimitException("the participant limit has been reached");
            }
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

//        запрос на участие от владельца события
        if (Objects.equals(event.getInitiator().getId(), userId))
            throw new ParticipationRequestsAddNotAllowedException("request from event owner is not allowed");

//        достигнут лимит участников
        Long acceptedRequestsNumber = event.getRequests().stream()
                .filter(request -> request.getStatus().equals(ParticipationRequestStatus.CONFIRMED))
                .count();
        if (Objects.equals(event.getParticipantLimit(), acceptedRequestsNumber))
            throw new ParticipationRequestsLimitException("the participant limit has been reached");

//        запрос на участие в неопубликованном событии
        if (!EventState.PUBLISHED.equals(event.getState()))
            throw new ParticipationRequestsAddNotAllowedException("cannot request participation in not published event");

//        повторный запрос на участие
        if (event.getRequests().stream().anyMatch(
                r -> Objects.equals(r.getRequester().getId(), userId) && Objects.equals(r.getEvent().getId(), eventId)))
            throw new ParticipationRequestsDuplicateException("request is present");


        ParticipationRequest newRequest;
        if (event.getRequestModeration()) {
            newRequest = ParticipationRequestMapper.toParticipationRequest(user,
                ParticipationRequestStatus.PENDING, event);
        }
        else {
            newRequest = ParticipationRequestMapper.toParticipationRequest(user,
                    ParticipationRequestStatus.CONFIRMED, event);
        }

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

        request.setStatus(ParticipationRequestStatus.CANCELED);

        ParticipationRequest canceledRequest = participationRequestRepository.save(request);

        return ParticipationRequestMapper.toParticipationRequestDto(canceledRequest);
    }
}
