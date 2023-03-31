package ru.practicum.mainservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {

    @ManyToMany
    private List<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;

    @Column(name = "compilation_is_pinned")
    private Boolean pinned;

    @NotBlank
    @Column(name = "compilation_title")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Compilation compilation = (Compilation) o;
        return id != null && Objects.equals(id, compilation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
