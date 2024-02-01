package com.coursework.coursework.mapper.event;

import com.coursework.coursework.mapper.organizer.OrganizerRecordMapper;
import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.organizer.entity.Organizer;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.EVENT_MANAGEMENT_TABLE;
import static com.coursework.coursework.utils.TableAliases.ORGANIZER_TABLE;

@Component
public class EventManagementRecordMapper {
    private final EventRecordMapper eventRecordMapper;
    private final OrganizerRecordMapper organizerRecordMapper;

    public EventManagementRecordMapper(EventRecordMapper eventRecordMapper,
                                       OrganizerRecordMapper organizerRecordMapper) {
        this.eventRecordMapper = eventRecordMapper;
        this.organizerRecordMapper = organizerRecordMapper;
    }

    public EventManagementProcess mapEventManagementProcess(Record record) {
        return mapEventManagementProcess(record, EVENT_MANAGEMENT_TABLE);
    }

    public EventManagementProcess mapEventManagementProcess(Record record, com.coursework.coursework.models.tables.OrganizerManagesEvent alias) {
        EventManagementProcess eventManagementProcess = new EventManagementProcess();
        eventManagementProcess.setId(record.get(alias.ID));
        eventManagementProcess.setEventId(record.get(alias.EVENT_ID));
        eventManagementProcess.setOrganizerId(record.get(alias.ORGANIZER_ID));

        Event event = eventRecordMapper.mapEvent(record);
        Organizer organizer = organizerRecordMapper.mapOrganizer(record, ORGANIZER_TABLE);

        eventManagementProcess.setEvent(event);
        eventManagementProcess.setOrganizer(organizer);

        return eventManagementProcess;
    }
}
