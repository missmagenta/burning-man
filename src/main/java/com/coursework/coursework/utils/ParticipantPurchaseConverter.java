package com.coursework.coursework.utils;

import com.coursework.coursework.model.participant.entity.ParticipantPurchase;
import com.coursework.coursework.model.participant.payload.ParticipantPurchasePayload;
import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import com.coursework.coursework.model.purchase.entity.Ticket;

public class ParticipantPurchaseConverter {
    public static ParticipantPurchasePayload convert(ParticipantPurchase record,
                                                     ParticipantRegistration participantRegistration,
                                                     Ticket ticket) {
        ParticipantPurchasePayload payload = new ParticipantPurchasePayload();
        payload.setId(record.getId());
        payload.setEventName(participantRegistration.getEventRegistration().getEventManagementProcess().getEvent().getName());
        payload.setPersonName(participantRegistration.getPerson().getName());
        payload.setPersonSurname(participantRegistration.getPerson().getSurname());
        payload.setPersonEmail(participantRegistration.getPerson().getEmail());
        payload.setPersonPhone(participantRegistration.getPerson().getContactNumber());
        payload.setParticipantRegistrationId(record.getParticipantRegistrationId());
        payload.setEventTicketSalesId(record.getEventTicketSalesId());
        payload.setTicketsAmountPurchased(record.getTicketsAmountPurchased());
        payload.setPurchaseDateTime(record.getPurchaseDateTime());
        payload.setPrice(participantRegistration.getEventRegistration().getPrice());
        payload.setTicketName(ticket.getName());

        return payload;
    }
}
