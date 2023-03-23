package ru.practicum.mainservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidEventParametersException.class })
    public ResponseEntity<Object> handleNewEventDateException(InvalidEventParametersException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ EventChangeNotAllowedException.class })
    public ResponseEntity<Object> handleEventChangeNotAllowedException(EventChangeNotAllowedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ParticipationRequestsLimitException.class })
    public ResponseEntity<Object> handleParticipationRequestsLimitException(ParticipationRequestsLimitException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ EventWithWrongStateException.class })
    public ResponseEntity<Object> handleEventWithWrongStateException(EventWithWrongStateException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ParticipationRequestsChangeStatusException.class })
    public ResponseEntity<Object> handleParticipationRequestsChangeStatusException(
            ParticipationRequestsChangeStatusException e
    ) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ParticipationRequestsDuplicateException.class })
    public ResponseEntity<Object> handleParticipationRequestsDuplicateException(ParticipationRequestsDuplicateException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ParticipationRequestsAddNotAllowedException.class })
    public ResponseEntity<Object> handleParticipationRequestsAddNotAllowedException(ParticipationRequestsAddNotAllowedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ IncorrectDataException.class })
    public ResponseEntity<Object> handleIncorrectDataException(IncorrectDataException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DublicateNameException.class })
    public ResponseEntity<Object> handleDublicateNameException(DublicateNameException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ CategoryIsInUseException.class })
    public ResponseEntity<Object> handleCategoryIsInUseException(CategoryIsInUseException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("{\"status\": \""+e.getMessage()+"\"}", HttpStatus.CONFLICT);
    }

}
