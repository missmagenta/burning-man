package com.coursework.coursework.model.purchase.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRegistrationPayload {
    private Integer id;
    private String eventName;
    private Integer eventManagementProcessId;
    private Integer ticketId;
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer eventId;
    private String ticket;
}
