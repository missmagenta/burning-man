package com.coursework.coursework.model.participant.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipantPurchasePayload {
    private Integer id;
    private String eventName;
    private String personName;
    private String personEmail;
    private String personPhone;
    private String personSurname;
    private String document;
    private String ticketName;
    private Double price;
    private Integer eventTicketSalesId;
    private Integer participantRegistrationId;
    private LocalDateTime purchaseDateTime;
    private Integer ticketsAmountPurchased;
}
