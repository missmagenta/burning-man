package com.coursework.coursework.mapper.event;

import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.event.entity.EventArchive;
import com.coursework.coursework.model.event.entity.MainTheme;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.EVENT_ARCHIVE_TABLE;

@Component
public class EventArchiveRecordMapper {
    private final EventRecordMapper eventRecordMapper;
    private final MainThemeRecordMapper mainThemeRecordMapper;

    public EventArchiveRecordMapper(EventRecordMapper eventRecordMapper,
                                    MainThemeRecordMapper mainThemeRecordMapper) {
        this.eventRecordMapper = eventRecordMapper;
        this.mainThemeRecordMapper = mainThemeRecordMapper;
    }

    public EventArchive map(Record record) {
        return map(record, EVENT_ARCHIVE_TABLE);
    }

    public EventArchive map(Record record, com.coursework.coursework.models.tables.EventArchive alias) {
        EventArchive eventArchive = new EventArchive();
        eventArchive.setId(record.get(alias.ID));
        eventArchive.setEventId(record.get(alias.EVENT_ID));
        eventArchive.setThemeId(record.get(alias.MAIN_THEME_CANDIDATES_ID));

        Event event = eventRecordMapper.mapEvent(record);
        MainTheme mainTheme = mainThemeRecordMapper.mapMainTheme(record);
        eventArchive.setEvent(event);
        eventArchive.setMainTheme(mainTheme);

        return eventArchive;
    }
}
