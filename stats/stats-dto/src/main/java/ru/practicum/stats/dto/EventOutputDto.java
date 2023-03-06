package ru.practicum.stats.dto;

import java.time.LocalDateTime;

public class EventOutputDto {
    private String app;
    private String uri;
    private LocalDateTime eventCreated;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LocalDateTime getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(LocalDateTime eventCreated) {
        this.eventCreated = eventCreated;
    }
}
