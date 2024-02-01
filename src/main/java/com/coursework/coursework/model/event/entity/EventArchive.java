package com.coursework.coursework.model.event.entity;

import lombok.Data;

@Data
public class EventArchive {
    private Integer id;
    private Integer eventId;
    private Integer themeId;

    private Event event;
    private MainTheme mainTheme;
}
