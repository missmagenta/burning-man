package com.coursework.coursework.model.purchase.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventSalesPayload {
    private Integer id;
    private String eventName;
    private String ticket;
    private Double price;
    private Integer registrationId;
    private Integer ticketsAvailable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
