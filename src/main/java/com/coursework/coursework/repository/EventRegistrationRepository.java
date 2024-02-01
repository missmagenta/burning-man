package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.event.EventManagementRecordMapper;
import com.coursework.coursework.mapper.purchase.EventRegistrationRecordMapper;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.purchase.entity.EventRegistration;
import com.coursework.coursework.models.routines.FinishTicketRegistration;
import com.coursework.coursework.models.routines.StartTicketRegistration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class EventRegistrationRepository {
    private final DSLContext dslContext;
    private final EventRegistrationRecordMapper eventRegistrationRecordMapper;
    private final EventManagementRecordMapper eventManagementRecordMapper;

    @Autowired
    public EventRegistrationRepository(DSLContext dslContext, EventRegistrationRecordMapper eventRegistrationRecordMapper, EventManagementRecordMapper eventManagementRecordMapper) {
        this.dslContext = dslContext;
        this.eventRegistrationRecordMapper = eventRegistrationRecordMapper;
        this.eventManagementRecordMapper = eventManagementRecordMapper;
    }

    @Transactional(readOnly = true)
    public EventRegistration findById(Integer id) {
        return dslContext.selectFrom(EVENT_REGISTRATION_TABLE)
                .where(EVENT_REGISTRATION_TABLE.ID.eq(id))
                .fetchOptional()
                .map(eventRegistrationRecordMapper::mapEventRegistration)
                .orElse(null);
    }

    @Transactional
    public List<EventRegistration> findAllPerEvent(Integer eventId) {
        return dslContext.select()
                .from(EVENT_REGISTRATION_TABLE)
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
                .where(EVENT_TABLE.ID.eq(eventId))
                .fetch()
                .map(eventRegistrationRecordMapper::mapEventRegistration);
    }

    @Transactional
    public List<EventRegistration> findAllPerOrganizer(Integer curOrgId) {
        return dslContext.select()
                .from(EVENT_REGISTRATION_TABLE)
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
                .map(eventRegistrationRecordMapper::mapEventRegistration);
    }

    @Transactional
    public EventManagementProcess findUpcomingOmeId(Integer curOrgId) {
        LocalDateTime pattern = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        return dslContext.select()
                .from(EVENT_MANAGEMENT_TABLE)
                .join(EVENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .where(ORGANIZER_TABLE.PERSON_ID.eq(curOrgId))
                .and(EVENT_TABLE.START_DATE.greaterThan(pattern))
                .fetchOptional()
                .map(eventManagementRecordMapper::mapEventManagementProcess)
                .orElse(null);
    }

    @Transactional
    public void startRegistration(Integer eventManagementId,
                                  Integer ticketTypeId,
                                  Double price) {
        StartTicketRegistration startTicketRegistration = new StartTicketRegistration();
        startTicketRegistration.setCurOrgManagesEventId(eventManagementId);
        startTicketRegistration.setCurTicketTypeId(ticketTypeId);
        startTicketRegistration.setCurPrice(price);
        startTicketRegistration.execute(dslContext.configuration());
    }

    @Transactional
    public Integer finishRegistration(Integer registrationId) {
        FinishTicketRegistration finishTicketRegistration = new FinishTicketRegistration();
        finishTicketRegistration.setCurTicketRegId(registrationId);
        finishTicketRegistration.execute(dslContext.configuration());
        return finishTicketRegistration.getReturnValue();
    }
}
