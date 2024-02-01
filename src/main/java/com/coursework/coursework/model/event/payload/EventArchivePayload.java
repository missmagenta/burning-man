package com.coursework.coursework.model.event.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventArchivePayload {
    private Integer id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String coordinates;
    private Integer capacity;
    private String eventStatus;

    private String mainThemeName;
    private String mainThemeDescription;
}
