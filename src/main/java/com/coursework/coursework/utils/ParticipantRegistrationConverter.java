package com.coursework.coursework.utils;

import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import com.coursework.coursework.model.participant.payload.ParticipantRegistrationPayload;
import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.model.purchase.entity.Ticket;
import com.coursework.coursework.model.purchase.entity.EventRegistration;

public class ParticipantRegistrationConverter {
    public static ParticipantRegistrationPayload convert(ParticipantRegistration record,
                                                         EventRegistration eventRegistration,
                                                         Ticket ticket,
                                                         Person person) {
        ParticipantRegistrationPayload payload = new ParticipantRegistrationPayload();
        payload.setId(record.getId());
        payload.setPersonId(record.getPersonId());
        payload.setEventName(eventRegistration.getEventManagementProcess().getEvent().getName());
        payload.setEventRegistrationId(record.getEventRegistrationId());
        payload.setRegistrationDateTime(record.getRegistrationDateTime());
        payload.setTicketName(ticket.getName());
        payload.setTicketDescription(ticket.getDescription());
        payload.setPrice(eventRegistration.getPrice());
        payload.setPersonName(person.getName());
        payload.setPersonSurname(person.getSurname());
        payload.setPersonEmail(person.getEmail());
        return payload;
    }
}
