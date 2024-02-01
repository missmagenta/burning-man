package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.purchase.EventTicketSalesRecordMapper;
import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import com.coursework.coursework.models.routines.FinishSales;
import com.coursework.coursework.models.routines.StartSales;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class EventTicketSalesRepository {
    private final DSLContext dslContext;
    private final EventTicketSalesRecordMapper eventTicketSalesMapper;

    public EventTicketSalesRepository(DSLContext dslContext, EventTicketSalesRecordMapper eventTicketSalesMapper) {
        this.dslContext = dslContext;
        this.eventTicketSalesMapper = eventTicketSalesMapper;
    }

    @Transactional(readOnly = true)
    public EventTicketSales findById(Integer id) {
        return dslContext.select()
                .from(EVENT_SALES_TABLE)
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(EVENT_SALES_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID.eq(TICKET_TABLE.ID))
                .join(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID.eq(EVENT_MANAGEMENT_TABLE.ID))
                .join(EVENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .where(EVENT_SALES_TABLE.ID.eq(id))
                .fetchOptional()
                .map(eventTicketSalesMapper::mapEventTicketSales)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public EventTicketSales findByRegistrationId(Integer regId) {
        return dslContext.select()
                .from(EVENT_SALES_TABLE)
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(EVENT_SALES_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID.eq(TICKET_TABLE.ID))
                .join(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID.eq(EVENT_MANAGEMENT_TABLE.ID))
                .join(EVENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .where(EVENT_SALES_TABLE.REGISTRATION_ID.eq(regId))
                .fetchOptional()
                .map(eventTicketSalesMapper::mapEventTicketSales)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<EventTicketSales> findAllPerOrganizer(Integer curOrgId) {
        return dslContext.select()
                .from(EVENT_SALES_TABLE)
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(EVENT_SALES_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID.eq(TICKET_TABLE.ID))
                .join(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID.eq(EVENT_MANAGEMENT_TABLE.ID))
                .join(EVENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .where(ORGANIZER_TABLE.PERSON_ID.eq(curOrgId))
                .fetch()
                .map(eventTicketSalesMapper::mapEventTicketSales);
    }

    @Transactional
    public List<EventTicketSales> findAllPerEvent(Integer eventId) {
        return dslContext.select()
                .from(EVENT_SALES_TABLE)
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_SALES_TABLE.REGISTRATION_ID.eq(EVENT_REGISTRATION_TABLE.ID))
                .join(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID.eq(EVENT_MANAGEMENT_TABLE.ID))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .join(EVENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .where(EVENT_TABLE.ID.eq(eventId))
                .fetch(eventTicketSalesMapper::mapEventTicketSales);
    }

    @Transactional
    public Integer startSales(Integer regId, Integer ticketsAvailable) {
        StartSales startSales = new StartSales();
        startSales.setCurRegId(regId);
        startSales.setCurTicketsAvailable(ticketsAvailable);
        startSales.execute(dslContext.configuration());
        return startSales.getReturnValue();
    }

    @Transactional
    public Integer finishSales(Integer salesId) {
        FinishSales finishEventSales = new FinishSales();
        finishEventSales.setCurSalesId(salesId);
        finishEventSales.execute(dslContext.configuration());
        return finishEventSales.getReturnValue();
    }
}
