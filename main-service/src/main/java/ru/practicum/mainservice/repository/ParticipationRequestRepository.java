package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.models.ParticipationRequest;
import ru.practicum.mainservice.models.ParticipationRequestStatus;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByEvent_IdAndStatus(Long eventId, ParticipationRequestStatus state);

    List<ParticipationRequest> findAllByEvent_Id(Long eventId);

    List<ParticipationRequest> findAllByEvent_IdAndEvent_Initiator_Id(Long eventId, Long userId);

    List<ParticipationRequest> findAllByIdInAndEventIdAndEvent_Initiator_Id(List<Long> ids, Long eventId, Long userId);

    List<ParticipationRequest> findAllByStatusAndEvent_Initiator_Id(ParticipationRequestStatus state, Long userId);


    List<ParticipationRequest> findAllByRequester_Id(Long requesterId);
}
