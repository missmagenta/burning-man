package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.participant.ParticipantRecordMapper;
import com.coursework.coursework.model.participant.entity.Participant;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.coursework.coursework.utils.TableAliases.PARTICIPANT_TABLE;

@Repository
public class ParticipantRepository {
    private final DSLContext dslContext;
    private final ParticipantRecordMapper participantRecordMapper;

    @Autowired
    public ParticipantRepository(DSLContext dslContext, ParticipantRecordMapper participantRecordMapper) {
        this.dslContext = dslContext;
        this.participantRecordMapper = participantRecordMapper;
    }

    @Transactional(readOnly = true)
    public Participant getByPersonId(Integer personId) {
        return dslContext.selectFrom(PARTICIPANT_TABLE)
                .where(PARTICIPANT_TABLE.PERSON_ID.eq(personId))
                .limit(1)
                .fetchOptional()
                .map(participantRecordMapper::mapParticipant)
                .orElse(null);
    }

    @Transactional
    public void add(Integer partPurId, Integer personId, String passport) {
        dslContext.insertInto(PARTICIPANT_TABLE)
                .set(PARTICIPANT_TABLE.PARTICIPANT_PURCHASE_ID, partPurId)
                .set(PARTICIPANT_TABLE.PERSON_ID, personId)
                .set(PARTICIPANT_TABLE.DOCUMENT_DETAILS, passport)
                .execute();
    }
}
