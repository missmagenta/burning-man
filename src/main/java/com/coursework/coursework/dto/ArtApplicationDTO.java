package com.coursework.coursework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class ArtApplicationDTO {
    private String artObject;
    private String description;
    private Double time;
    private String location;
    private String material;
    private String light;
    private String link;

    private Integer eventId;
    private Integer participantId;
}
