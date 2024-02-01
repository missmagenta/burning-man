package com.coursework.coursework.model.event.payload;

import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.organizer.entity.Organizer;
import lombok.Data;

@Data
public class EventManagementProcessPayload {
    private Integer id;
    private Event event;
    private Organizer organizer;
}
