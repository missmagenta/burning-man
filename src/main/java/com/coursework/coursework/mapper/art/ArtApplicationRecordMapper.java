package com.coursework.coursework.mapper.art;

import com.coursework.coursework.mapper.event.EventRecordMapper;
import com.coursework.coursework.mapper.participant.ParticipantRecordMapper;
import com.coursework.coursework.model.art.entity.ArtApplication;
import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.participant.entity.Participant;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.ART_APPLICATION_TABLE;

@Component
public class ArtApplicationRecordMapper {

    private final ParticipantRecordMapper participantRecordMapper;
    private final EventRecordMapper eventRecordMapper;

    public ArtApplicationRecordMapper(ParticipantRecordMapper participantRecordMapper,
                                      EventRecordMapper eventRecordMapper) {
        this.participantRecordMapper = participantRecordMapper;
        this.eventRecordMapper = eventRecordMapper;
    }

    public ArtApplication mapArtApplication(Record record) {
        ArtApplication artApplication = new ArtApplication();

        artApplication.setId(record.get(ART_APPLICATION_TABLE.ID));
        artApplication.setEventId(record.get(ART_APPLICATION_TABLE.EVENT_ID));
        artApplication.setParticipantId(record.get(ART_APPLICATION_TABLE.PARTICIPANT_ID));
        artApplication.setName(record.get(ART_APPLICATION_TABLE.NAME));
        artApplication.setDescription(record.get(ART_APPLICATION_TABLE.DESCRIPTION));
        artApplication.setApplicationDate(record.get(ART_APPLICATION_TABLE.APPLICATION_DATE));
        artApplication.setMaterial(record.get(ART_APPLICATION_TABLE.MATERIAL));
        artApplication.setLightingEquipment(record.get(ART_APPLICATION_TABLE.LIGHTING_EQUIPMENT));
        artApplication.setDesiredLocation(record.get(ART_APPLICATION_TABLE.DESIRED_LOCATION));
        artApplication.setApproximateInstallationTime(record.get(ART_APPLICATION_TABLE.REQUIRED_INSTALLATION_TIME));
        artApplication.setModelLink(record.get(ART_APPLICATION_TABLE.MODEL_LINK_3D));

        Participant participant = participantRecordMapper.mapParticipant(record);
        Event event = eventRecordMapper.mapEvent(record);

        artApplication.setParticipant(participant);
        artApplication.setEvent(event);

        return artApplication;
    }

}
