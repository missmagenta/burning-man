package com.coursework.coursework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventIdDTO {
    @NotNull
    private Integer eventId;
}
