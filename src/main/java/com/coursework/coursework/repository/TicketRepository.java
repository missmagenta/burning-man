package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.ticket.TicketTypeRecordMapper;
import com.coursework.coursework.model.purchase.entity.Ticket;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class TicketRepository {
    private final DSLContext dslContext;
    private final TicketTypeRecordMapper ticketMapper;

    public TicketRepository(DSLContext dslContext, TicketTypeRecordMapper ticketMapper) {
        this.dslContext = dslContext;
        this.ticketMapper = ticketMapper;
    }

    @Transactional
    public List<Ticket> findAll() {
        return findAll(DSL.trueCondition());
    }

    @Transactional(readOnly = true)
    public List<Ticket> findAll(Condition condition) {
        return dslContext.selectFrom(TICKET_TABLE)
                .where(condition)
                .fetch()
                .map(ticketMapper::mapTicket);
    }
}
