package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.event.EventManagementRecordMapper;
import com.coursework.coursework.mapper.event.EventRecordMapper;
import com.coursework.coursework.mapper.organizer.OrganizerRecordMapper;
import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.organizer.entity.Organizer;
import com.coursework.coursework.models.enums.EventStatus;
import com.coursework.coursework.models.routines.AssignOrganizerToEvent;
import com.coursework.coursework.models.routines.UpdateEventStatus;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class EventManagementProcessRepository {

    private final DSLContext dslContext;
    private final EventRecordMapper eventRecordMapper;
    private final OrganizerRecordMapper organizerRecordMapper;
    private final EventManagementRecordMapper eventManagementRecordMapper;

    public EventManagementProcessRepository(DSLContext dslContext, EventRecordMapper eventRecordMapper, OrganizerRecordMapper organizerRecordMapper, EventManagementRecordMapper eventManagementRecordMapper) {
        this.dslContext = dslContext;
        this.eventRecordMapper = eventRecordMapper;
        this.organizerRecordMapper = organizerRecordMapper;
        this.eventManagementRecordMapper = eventManagementRecordMapper;
    }

    @Transactional
    public void assignOrganizerToEvent(Integer eventId, Integer organizerId) {
        AssignOrganizerToEvent helper = new AssignOrganizerToEvent();
        helper.setEventIdInput(eventId);
        helper.setOrganizerIdInput(organizerId);
        helper.execute(dslContext.configuration());
    }

    @Transactional
    public void updateStatus(Integer eventId, String status) {
        EventStatus eventStatus = EventStatus.valueOf(status);

        UpdateEventStatus updateEventStatus = new UpdateEventStatus();
        updateEventStatus.setEventIdInput(eventId);
        updateEventStatus.setNewStatus(eventStatus);
        updateEventStatus.execute(dslContext.configuration());
    }

    @Transactional(readOnly = true)
    public List<Event> findFutureEvents() {
        LocalDateTime startDateThreshold = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        return dslContext.selectFrom(EVENT_TABLE)
                .where(EVENT_TABLE.START_DATE.greaterThan(startDateThreshold))
                .fetch()
                .map(eventRecordMapper::mapEvent);
    }

    @Transactional(readOnly = true)
    public List<Organizer> findAliveOrgs() {
        LocalDate dateThreshold = LocalDate.of(2005, 1, 1);
        return dslContext.select()
                .from(ORGANIZER_TABLE)
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .where(PERSON_TABLE.DEATH_DATE.isNull())
                .and(PERSON_TABLE.BIRTH_DATE.lessThan(dateThreshold))
                .fetch()
                .map(organizerRecordMapper::mapOrganizer);
    }

    @Transactional(readOnly = true)
    public List<EventManagementProcess> findFutureOme() {
        LocalDateTime startDateThreshold = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        return dslContext.select()
                .from(EVENT_MANAGEMENT_TABLE)
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .where(EVENT_TABLE.START_DATE.greaterThan(startDateThreshold))
                .fetch()
                .map(eventManagementRecordMapper::mapEventManagementProcess);
    }
}
