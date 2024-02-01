package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.organizer.OrganizerRecordMapper;
import com.coursework.coursework.model.organizer.entity.Organizer;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class OrganizerRepository {
    private final DSLContext dslContext;
    private final OrganizerRecordMapper organizerRecordMapper;

    @Autowired
    public OrganizerRepository(DSLContext dslContext, OrganizerRecordMapper organizerRecordMapper) {
        this.dslContext = dslContext;
        this.organizerRecordMapper = organizerRecordMapper;
    }

    @Transactional(readOnly = true)
    public Organizer getByPersonId(Integer personID) {
        return dslContext.select()
                .from(ORGANIZER_TABLE)
                .innerJoin(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(ORGANIZER_TABLE.PERSON_ID))
                .join(ROLE_TABLE)
                .on(ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID))
                .where(ORGANIZER_TABLE.PERSON_ID.eq(personID))
                .fetchOptional()
                .map(organizerRecordMapper::mapOrganizer)
                .orElse(null);
    }
}
