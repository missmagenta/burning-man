package com.coursework.coursework.model.art.entity;

import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.participant.entity.Participant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArtApplication {
    private Integer id;
    private Integer eventId;
    private Integer participantId;
    private String name;
    private String description;
    private LocalDateTime applicationDate;
    private String material;
    private String lightingEquipment;
    private String desiredLocation;
    private Double approximateInstallationTime;
    private String modelLink;

    private Participant participant;
    private Event event;
}
