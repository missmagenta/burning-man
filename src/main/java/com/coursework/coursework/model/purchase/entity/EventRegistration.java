package com.coursework.coursework.model.purchase.entity;

import com.coursework.coursework.model.event.entity.EventManagementProcess;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRegistration {
    private Integer id;
    private Integer eventManagementProcessId;
    private Integer ticketId;
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private EventManagementProcess eventManagementProcess;
    private Ticket ticket;
}
