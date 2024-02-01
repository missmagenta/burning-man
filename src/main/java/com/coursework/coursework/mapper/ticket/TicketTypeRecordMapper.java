package com.coursework.coursework.mapper.ticket;

import com.coursework.coursework.model.purchase.entity.Ticket;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.TICKET_TABLE;

@Component
public class TicketTypeRecordMapper {

    public Ticket mapTicket(Record record) {
        return mapTicket(record, TICKET_TABLE);
    }

    public Ticket mapTicket(Record record, com.coursework.coursework.models.tables.TicketType alias) {
        Ticket ticket = new Ticket();
        ticket.setId(record.get(alias.ID));
        ticket.setName(record.get(alias.NAME));
        ticket.setDescription(record.get(alias.DESCRIPTION));
        return ticket;
    }
}
