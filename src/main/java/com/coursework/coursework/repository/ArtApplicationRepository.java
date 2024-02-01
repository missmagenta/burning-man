package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.art.ArtApplicationRecordMapper;
import com.coursework.coursework.model.art.entity.ArtApplication;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class ArtApplicationRepository {
    private final DSLContext dslContext;
    private final ArtApplicationRecordMapper artApplicationRecordMapper;

    @Autowired
    public ArtApplicationRepository(DSLContext dslContext, ArtApplicationRecordMapper artApplicationRecordMapper) {
        this.dslContext = dslContext;
        this.artApplicationRecordMapper = artApplicationRecordMapper;
    }

    @Transactional
    public List<ArtApplication> getAllArtApplications() {
        return dslContext.selectFrom(ART_APPLICATION_TABLE)
                .fetch()
                .map(r -> artApplicationRecordMapper.mapArtApplication(r));
    }

    @Transactional(readOnly = true)
    public List<ArtApplication> findByPersonId(Integer personId) {
        return dslContext.select()
                .from(ART_APPLICATION_TABLE)
                .join(PARTICIPANT_TABLE)
                .on(PARTICIPANT_TABLE.ID.eq(ART_APPLICATION_TABLE.PARTICIPANT_ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_TABLE.PERSON_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(ART_APPLICATION_TABLE.EVENT_ID))
                .where(PARTICIPANT_TABLE.PERSON_ID.eq(personId))
                .fetch()
                .map(artApplicationRecordMapper::mapArtApplication);
    }

    @Transactional
    public void addApplication(
            Integer eventId,
            Integer participantId,
            String artName,
            String description,
            String material,
            String light,
            Double time,
            String location,
            String link
    ) {
        dslContext.insertInto(ART_APPLICATION_TABLE)
                .set(ART_APPLICATION_TABLE.EVENT_ID, eventId)
                .set(ART_APPLICATION_TABLE.PARTICIPANT_ID, participantId)
                .set(ART_APPLICATION_TABLE.NAME, artName)
                .set(ART_APPLICATION_TABLE.DESCRIPTION, description)
                .set(ART_APPLICATION_TABLE.APPLICATION_DATE, LocalDateTime.now())
                .set(ART_APPLICATION_TABLE.MATERIAL, material)
                .set(ART_APPLICATION_TABLE.LIGHTING_EQUIPMENT, light)
                .set(ART_APPLICATION_TABLE.DESIRED_LOCATION, location)
                .set(ART_APPLICATION_TABLE.REQUIRED_INSTALLATION_TIME, time)
                .set(ART_APPLICATION_TABLE.MODEL_LINK_3D, link)
                .execute();
    }


}
