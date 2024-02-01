package com.coursework.coursework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartRegDTO {
    @NotNull
    private Integer regId;
    @NotNull
    private Integer personId;
}
