package com.coursework.coursework.mapper.purchase;

import com.coursework.coursework.model.purchase.entity.EventRegistration;
import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.EVENT_SALES_TABLE;

@Component
public class EventTicketSalesRecordMapper {
    private final EventRegistrationRecordMapper eventRegistrationMapper;

    public EventTicketSalesRecordMapper(EventRegistrationRecordMapper eventRegistrationMapper) {
        this.eventRegistrationMapper = eventRegistrationMapper;
    }

    public EventTicketSales mapEventTicketSales(Record record) {
        EventTicketSales eventTicketSales = new EventTicketSales();

        eventTicketSales.setId(record.get(EVENT_SALES_TABLE.ID));
        eventTicketSales.setEventRegistrationId(record.get(EVENT_SALES_TABLE.REGISTRATION_ID));
        eventTicketSales.setStartDateTime(record.get(EVENT_SALES_TABLE.START_DATE));
        eventTicketSales.setEndDateTime(record.get(EVENT_SALES_TABLE.END_DATE));
        eventTicketSales.setTicketsAvailable(record.get(EVENT_SALES_TABLE.TICKETS_AVAILABLE));

        EventRegistration eventRegistration = eventRegistrationMapper.mapEventRegistration(record);

        eventTicketSales.setEventRegistration(eventRegistration);

        return eventTicketSales;
    }
}
