package ru.practicum.mainservice.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.*;
import ru.practicum.mainservice.service.ParticipationRequestService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ParticipationRequestPrivateController {

    private final ParticipationRequestService participationRequestService;

    @GetMapping
    public List<ParticipationRequestDto> get(@Min(0) @NotNull @PathVariable Long userId) {
        log.info("GET /users/{}/requests", userId);
        return participationRequestService.get(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ParticipationRequestDto save(@Min(0) @NotNull @PathVariable Long userId,
                                        @Min(0) @NotNull @RequestParam Long eventId) {
        log.info("POST /users/" + userId + "/requests?eventId=" + eventId);
        return participationRequestService.save(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@Min(0) @NotNull @PathVariable Long userId,
                                          @Min(0) @NotNull @PathVariable Long requestId) {
        log.info("POST /users/" + userId + "/requests/" + requestId + "/cancel");
        return participationRequestService.cancel(userId, requestId);
    }
}
