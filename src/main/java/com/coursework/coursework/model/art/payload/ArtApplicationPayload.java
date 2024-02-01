package com.coursework.coursework.model.art.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArtApplicationPayload {
    private Integer id;

    private Integer eventId;
    private String eventName;

    private Integer participantId;

    private String name;
    private String description;
    private LocalDateTime applicationDate;
    private String material;
    private String lightingEquipment;
    private String desiredLocation;
    private Double approximateInstallationTime;
    private String modelLink;
}
