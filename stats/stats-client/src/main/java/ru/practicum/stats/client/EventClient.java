package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.EventInputDto;
import ru.practicum.stats.dto.EventOutputDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventClient extends BaseClient {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public EventClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addStat(EventInputDto eventInputDto) {
        return post("/hit", null, eventInputDto);
    }

    public List<EventOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/stats");
        pathBuilder.append("?start=");
        pathBuilder.append(start.format(formatter));
        pathBuilder.append("&end=");
        pathBuilder.append(end.format(formatter));
        pathBuilder.append("&unique=");
        pathBuilder.append(unique);
        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                pathBuilder.append("&uri=");
                pathBuilder.append(uri);
            }
        }

        return (List<EventOutputDto>) get(pathBuilder.toString(), null).getBody();
    }
}
