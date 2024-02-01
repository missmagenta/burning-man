package com.coursework.coursework.model.purchase.entity;

import com.coursework.coursework.model.purchase.entity.EventRegistration;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventTicketSales {
    private Integer id;
    private Integer eventRegistrationId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer ticketsAvailable;

    private EventRegistration eventRegistration;
}
