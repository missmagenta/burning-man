package com.coursework.coursework.mapper.participant;

import com.coursework.coursework.mapper.person.PersonRecordMapper;
import com.coursework.coursework.mapper.purchase.EventRegistrationRecordMapper;
import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.model.purchase.entity.EventRegistration;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coursework.coursework.utils.TableAliases.PARTICIPANT_REGISTRATION_TABLE;

@Component
public class ParticipantRegistrationRecordMapper {
    private final PersonRecordMapper personRecordMapper;
    private final EventRegistrationRecordMapper eventRegistrationMapper;

    public ParticipantRegistrationRecordMapper(PersonRecordMapper personRecordMapper,
                                               EventRegistrationRecordMapper eventRegistrationMapper) {
        this.personRecordMapper = personRecordMapper;
        this.eventRegistrationMapper = eventRegistrationMapper;
    }

    public ParticipantRegistration map(Record record) {
        Integer id = record.get(PARTICIPANT_REGISTRATION_TABLE.ID);
        Integer personId = record.get(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID);
        Integer eventRegistrationId = record.get(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID);
        LocalDateTime registrationDateTime = record.get(PARTICIPANT_REGISTRATION_TABLE.REG_DATE);

        Person person = personRecordMapper.mapPerson(record);
        EventRegistration registration = eventRegistrationMapper.mapEventRegistration(record);

        ParticipantRegistration participantRegistration = new ParticipantRegistration();
        participantRegistration.setId(id);
        participantRegistration.setPersonId(personId);
        participantRegistration.setEventRegistrationId(eventRegistrationId);
        participantRegistration.setRegistrationDateTime(registrationDateTime);
        participantRegistration.setPerson(person);
        participantRegistration.setEventRegistration(registration);

        return participantRegistration;
    }
}
