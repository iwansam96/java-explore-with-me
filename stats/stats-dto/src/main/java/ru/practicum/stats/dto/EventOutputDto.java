package ru.practicum.stats.dto;

public class EventOutputDto {

    public EventOutputDto(Long hits, String app, String uri) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

    private String app;
    private String uri;
    private Long hits;

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

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

    @Override
    public String toString() {
        return "EventOutputDto{" +
                "app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", hits=" + hits +
                '}';
    }
}
