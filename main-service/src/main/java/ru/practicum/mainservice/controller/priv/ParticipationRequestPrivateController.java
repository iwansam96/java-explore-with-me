package ru.practicum.mainservice.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.ParticipationRequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestPrivateController {

    private final ParticipationRequestService participationRequestService;

    @GetMapping
    public List<ParticipationRequestDto> get(@Valid @NotNull @PathVariable Long userId) {
        log.info("GET /users/{}/requests", userId);
        return participationRequestService.get(userId);
    }

    @PostMapping
    public ParticipationRequestDto save(@Valid @NotNull @PathVariable Long userId,
                                        @Valid @NotNull @RequestParam Long eventId) {
        log.info("POST /users/" + userId + "/requests?eventId=" + eventId);
        return participationRequestService.save(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto update(@Valid @NotNull @PathVariable Long userId,
                              @Valid @NotNull @PathVariable Long requestId) {
        log.info("POST /users/" + userId + "/requests/" + requestId + "/cancel");
        return participationRequestService.cancel(userId, requestId);
    }
}
