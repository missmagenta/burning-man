package com.coursework.coursework.mapper.purchase;

import com.coursework.coursework.mapper.event.EventManagementRecordMapper;
import com.coursework.coursework.mapper.ticket.TicketTypeRecordMapper;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.purchase.entity.Ticket;
import com.coursework.coursework.model.purchase.entity.EventRegistration;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coursework.coursework.utils.TableAliases.EVENT_REGISTRATION_TABLE;

@Component
public class EventRegistrationRecordMapper {
    private final EventManagementRecordMapper eventManagementRecordMapper;
    private final TicketTypeRecordMapper ticketTypeRecordMapper;

    public EventRegistrationRecordMapper(EventManagementRecordMapper eventManagementRecordMapper, TicketTypeRecordMapper ticketTypeRecordMapper) {
        this.eventManagementRecordMapper = eventManagementRecordMapper;
        this.ticketTypeRecordMapper = ticketTypeRecordMapper;
    }

    public EventRegistration mapEventRegistration(Record record) {
        Integer id = record.get(EVENT_REGISTRATION_TABLE.ID);
        Integer eventManagementProcessId = record.get(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID);
        Integer ticketTypeId = record.get(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID);
        Double price = record.get(EVENT_REGISTRATION_TABLE.PRICE);
        LocalDateTime startDate = record.get(EVENT_REGISTRATION_TABLE.START_DATE);
        LocalDateTime endDate = record.get(EVENT_REGISTRATION_TABLE.END_DATE);

        EventManagementProcess eventManagement = eventManagementRecordMapper.mapEventManagementProcess(record);
        Ticket ticket = ticketTypeRecordMapper.mapTicket(record);

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setId(id);
        eventRegistration.setEventManagementProcessId(eventManagementProcessId);
        eventRegistration.setTicketId(ticketTypeId);
        eventRegistration.setPrice(price);
        eventRegistration.setStartDate(startDate);
        eventRegistration.setEndDate(endDate);

        eventRegistration.setEventManagementProcess(eventManagement);
        eventRegistration.setTicket(ticket);

        return eventRegistration;
    }
}
