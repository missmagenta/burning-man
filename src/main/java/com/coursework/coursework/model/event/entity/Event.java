package com.coursework.coursework.model.event.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Integer id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String coordinates;
    private Integer capacity;
    private EventStatus eventStatus;
}
