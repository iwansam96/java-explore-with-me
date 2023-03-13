package ru.practicum.mainservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    //    Краткое описание
    @NotBlank
    @Column(name = "event_annotation")
    private String annotation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_category", referencedColumnName = "category_id", nullable = false)
    private Category category;

    //    Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(name = "event_created_on")
    private LocalDateTime createdOn;

    //    Полное описание события
    @NotBlank
    @Column(name = "event_description")
    private String description;

    //    Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_initiator", referencedColumnName = "user_id", nullable = false)
    private User initiator;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "event_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "event_lon"))
    })
    private Location location;

    @Column(name = "event_is_paid")
    private Boolean paid;

//    Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "event_participant_limit")
    private Long participantLimit;

//    Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "event_published")
    private LocalDateTime publishedOn;

//    Нужна ли пре-модерация заявок на участие
    @Column(name = "event_request_moderation")
    private Boolean requestModeration;

//    PENDING, PUBLISHED, CANCELED
    @Enumerated(EnumType.STRING)
    @Column(name = "event_state", nullable = false)
    private EventState state;

    @NotBlank
    @Column(name = "event_title")
    private String title;

    @ManyToMany
    private List<Compilation> compilations;

    @OneToMany(mappedBy = "event")
    private List<ParticipationRequest> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
