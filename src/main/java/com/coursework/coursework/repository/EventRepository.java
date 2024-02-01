package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.event.EventRecordMapper;
import com.coursework.coursework.model.event.entity.Event;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.coursework.coursework.utils.TableAliases.EVENT_TABLE;

@Repository
public class EventRepository {
    private final DSLContext dslContext;
    private final EventRecordMapper eventRecordMapper;

    public EventRepository(DSLContext dslContext, EventRecordMapper eventRecordMapper) {
        this.dslContext = dslContext;
        this.eventRecordMapper = eventRecordMapper;
    }

    @Transactional
    public Event findById(Integer id) {
        return dslContext.selectFrom(EVENT_TABLE)
                .where(EVENT_TABLE.ID.eq(id))
                .fetchOptional()
                .map(eventRecordMapper::mapEvent)
                .orElse(null);
    }
}
