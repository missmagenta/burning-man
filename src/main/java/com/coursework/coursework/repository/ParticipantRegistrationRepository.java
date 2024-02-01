package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.participant.ParticipantRegistrationRecordMapper;
import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class ParticipantRegistrationRepository {
    private DSLContext dslContext;
    private final ParticipantRegistrationRecordMapper participantRegistrationMapper;

    @Autowired
    public ParticipantRegistrationRepository(DSLContext dslContext,
                                             ParticipantRegistrationRecordMapper participantRegistrationMapper) {
        this.dslContext = dslContext;
        this.participantRegistrationMapper = participantRegistrationMapper;
    }

    @Transactional(readOnly = true)
    public List<ParticipantRegistration> findByPersonId(Integer personId) {
        return dslContext.select()
                .from(PARTICIPANT_REGISTRATION_TABLE)
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID))
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID))
                .innerJoin(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on((ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID)))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .where(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID.eq(personId))
                .fetch()
                .map(participantRegistrationMapper::map);
    }

    @Transactional(readOnly = true)
    public List<ParticipantRegistration> findByRegId(Integer regId) {
        return dslContext.select()
                .from(PARTICIPANT_REGISTRATION_TABLE)
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID))
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID))
                .innerJoin(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on((ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID)))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .where(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID.eq(regId))
                .fetch()
                .map(participantRegistrationMapper::map);
    }

    @Transactional
    public void addRegistration(
            Integer personId,
            Integer eventRegistrationId,
            LocalDateTime registrationTime
    ) {
        dslContext.insertInto(PARTICIPANT_REGISTRATION_TABLE)
                .set(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID, personId)
                .set(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID, eventRegistrationId)
                .set(PARTICIPANT_REGISTRATION_TABLE.REG_DATE, registrationTime)
                .execute();
    }
}
