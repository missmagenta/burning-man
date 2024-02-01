package com.coursework.coursework.model.event.entity;

import com.coursework.coursework.model.organizer.entity.Organizer;
import lombok.Data;

@Data
public class EventManagementProcess {
    private Integer id;
    private Integer eventId;
    private Integer organizerId;

    private Event event;
    private Organizer organizer;
}
