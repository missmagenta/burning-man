package com.coursework.coursework.utils;

import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.purchase.payload.EventRegistrationPayload;
import com.coursework.coursework.model.purchase.entity.EventRegistration;

public class EventRegistrationConverter {
    public static EventRegistrationPayload convertToPayload(EventRegistration record,
                                                            EventManagementProcess eventManagementProcess) {
        EventRegistrationPayload payload = new EventRegistrationPayload();

        payload.setId(record.getId());
        payload.setEventName(eventManagementProcess.getEvent().getName());
        payload.setEventManagementProcessId(record.getEventManagementProcess().getId());
        payload.setTicketId(record.getTicket().getId());
        payload.setPrice(record.getPrice());
        payload.setStartDate(record.getStartDate());
        payload.setEndDate(record.getEndDate());

        payload.setEventId(eventManagementProcess.getEventId());
        payload.setTicket(record.getTicket().getName());
        return payload;
    }

    public static EventRegistrationPayload convertOme(EventManagementProcess record) {
        if (record == null) return null;
        EventRegistrationPayload payload = new EventRegistrationPayload();

        payload.setEventManagementProcessId(record.getId());
        payload.setEventId(record.getEventId());
        return payload;
    }
}
