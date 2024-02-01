package com.coursework.coursework.mapper.event;

import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.event.entity.EventStatus;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.EVENT_TABLE;

@Component
public class EventRecordMapper {
    public Event mapEvent(Record record) {
        return mapEvent(record, EVENT_TABLE);
    }

    public Event mapEvent(Record record, com.coursework.coursework.models.tables.Event alias) {
        Event event = new Event();
        event.setId(record.get(alias.ID));
        event.setName(record.get(alias.NAME));
        event.setStartDate(record.get(alias.START_DATE));
        event.setEndDate(record.get(alias.END_DATE));
        event.setLocation(record.get(alias.LOCATION));
        event.setCoordinates(record.get(alias.COORDINATES));
        event.setCapacity(record.get(alias.CAPACITY));
        event.setEventStatus(convertEventStatus(record.get(alias.STATUS)));
        return event;
    }

    public EventStatus convertEventStatus(com.coursework.coursework.models.enums.EventStatus alias) {
        return EventStatus.valueOf(alias.name().toUpperCase());
    }
}
