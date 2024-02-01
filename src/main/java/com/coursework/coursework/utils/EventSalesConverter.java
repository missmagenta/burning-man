package com.coursework.coursework.utils;

import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import com.coursework.coursework.model.purchase.payload.EventSalesPayload;

import java.util.List;

public class EventSalesConverter {
    public static EventSalesPayload convertToPayload(EventTicketSales record) {
        if (record == null) return null;
        EventSalesPayload payload = new EventSalesPayload();

        payload.setId(record.getId());
        payload.setEventName(record.getEventRegistration().getEventManagementProcess().getEvent().getName());
        payload.setTicket(record.getEventRegistration().getTicket().getName());
        payload.setPrice(record.getEventRegistration().getPrice());
        payload.setRegistrationId(record.getEventRegistrationId());
        payload.setStartDateTime(record.getStartDateTime());
        payload.setEndDateTime(record.getEndDateTime());
        payload.setTicketsAvailable(record.getTicketsAvailable());
        return payload;
    }
}
