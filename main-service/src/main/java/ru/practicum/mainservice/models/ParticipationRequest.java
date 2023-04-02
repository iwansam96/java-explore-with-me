package ru.practicum.mainservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "participation_requests", schema = "public")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "participation_request_created")
    private LocalDateTime created;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_request_event", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "participation_request_requester", referencedColumnName = "user_id", nullable = false)
    private User requester;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_request_status", nullable = false)
    private ParticipationRequestStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ParticipationRequest participationRequest = (ParticipationRequest) o;
        return id != null && Objects.equals(id, participationRequest.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ParticipationRequest{" +
                "id=" + id +
                ", created=" + created +
                ", event=" + event +
                ", requester=" + requester +
                ", status=" + status +
                '}';
    }
}
