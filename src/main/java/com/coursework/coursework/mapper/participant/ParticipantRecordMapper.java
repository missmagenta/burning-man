package com.coursework.coursework.mapper.participant;

import com.coursework.coursework.mapper.person.PersonRecordMapper;
import com.coursework.coursework.model.participant.entity.Participant;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.PARTICIPANT_TABLE;

@Component
public class ParticipantRecordMapper {

    private final PersonRecordMapper personRecordMapper;

    public ParticipantRecordMapper(PersonRecordMapper personRecordMapper) {
        this.personRecordMapper = personRecordMapper;
    }

    public Participant mapParticipant(Record record) {
        return mapParticipant(record, PARTICIPANT_TABLE);
    }

    public Participant mapParticipant(Record record, com.coursework.coursework.models.tables.Participant alias) {
        Participant participant = new Participant();
        participant.setId(record.get(alias.ID));
        participant.setDocumentDetails(record.get(alias.DOCUMENT_DETAILS));
        participant.setParticipantPurchaseId(record.get(alias.PARTICIPANT_PURCHASE_ID));
        participant.setPersonId(record.get(alias.PERSON_ID));

        return participant;
    }
}
