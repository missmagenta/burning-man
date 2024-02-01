package com.coursework.coursework.model.participant.entity;

import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipantPurchase {
    private Integer id;
    private Integer eventTicketSalesId;
    private Integer participantRegistrationId;
    private LocalDateTime purchaseDateTime;
    private Integer ticketsAmountPurchased;

    private EventTicketSales eventTicketSales;
    private ParticipantRegistration participantRegistration;
}
