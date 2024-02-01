package com.coursework.coursework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartPurchaseDTO {
    @NotNull
    private Integer partRegId;
    @NotNull
    private String passport;
    @NotNull
    private Integer personId;
    @NotNull
    private Integer salesId;
    @NotNull
    private Integer ticketAmount;
}
