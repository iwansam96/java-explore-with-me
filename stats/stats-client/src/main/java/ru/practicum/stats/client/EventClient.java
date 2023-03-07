package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.EventInputDto;

import java.time.LocalDateTime;
import java.util.List;

public class EventClient extends BaseClient {

    public EventClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    ResponseEntity<Object> addStat(EventInputDto eventInputDto) {
        return post("/hit", null, eventInputDto);
    }

    ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("?start=");
        pathBuilder.append(start);
        pathBuilder.append("&end=");
        pathBuilder.append(end);
        pathBuilder.append("&unique=");
        pathBuilder.append(unique);
        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                pathBuilder.append("&uri=");
                pathBuilder.append(uri);
            }
        }

        return get("/stats", null);
    }
}
