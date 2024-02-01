package com.coursework.coursework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventOrganizerDTO {
    @NotNull
    private Integer eventId;
    @NotNull
    private Integer organizerId;
}
