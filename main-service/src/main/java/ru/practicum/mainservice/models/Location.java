package ru.practicum.mainservice.models;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {
//    Широта
    private Float lat;

//    Долгота
    private Float lon;
}
