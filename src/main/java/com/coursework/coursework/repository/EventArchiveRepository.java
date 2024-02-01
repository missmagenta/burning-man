package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.event.EventArchiveRecordMapper;
import com.coursework.coursework.model.event.entity.EventArchive;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class EventArchiveRepository {
    private final DSLContext dslContext;
    private final EventArchiveRecordMapper eventArchiveMapper;

    @Autowired
    public EventArchiveRepository(DSLContext dslContext,
                                  EventArchiveRecordMapper eventArchiveMapper) {
        this.dslContext = dslContext;
        this.eventArchiveMapper = eventArchiveMapper;
    }

    @Transactional(readOnly = true)
    public List<EventArchive> findAll() {
        return findAll(DSL.trueCondition());
    }

    @Transactional(readOnly = true)
    public List<EventArchive> findAll(Condition condition) {
        return dslContext.select()
                .from(EVENT_TABLE)
                .join(EVENT_ARCHIVE_TABLE)
                .on(EVENT_ARCHIVE_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(MAIN_THEME_TABLE)
                .on(MAIN_THEME_TABLE.ID.eq(EVENT_ARCHIVE_TABLE.MAIN_THEME_CANDIDATES_ID))
                .where(condition)
                .fetch()
                .map(eventArchiveMapper::map);
    }

    @Transactional
    public List<EventArchive> getEventContent(Integer recordId) {
        return dslContext.select()
                .from(EVENT_TABLE)
                .join(EVENT_ARCHIVE_TABLE)
                .on(EVENT_ARCHIVE_TABLE.EVENT_ID.eq(EVENT_TABLE.ID))
                .join(MAIN_THEME_TABLE)
                .on(MAIN_THEME_TABLE.ID.eq(EVENT_ARCHIVE_TABLE.MAIN_THEME_CANDIDATES_ID))
                .where(EVENT_ARCHIVE_TABLE.ID.eq(recordId))
                .fetch()
                .map(eventArchiveMapper::map);
    }
}
