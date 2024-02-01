package com.coursework.coursework.mapper.event;

import com.coursework.coursework.model.event.entity.MainTheme;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.MAIN_THEME_TABLE;

@Component
public class MainThemeRecordMapper {

    public MainTheme mapMainTheme(Record record) {
        return mapMainTheme(record, MAIN_THEME_TABLE);
    }

    public MainTheme mapMainTheme(Record record, com.coursework.coursework.models.tables.MainThemeCandidates alias) {
        MainTheme mainTheme = new MainTheme();
        mainTheme.setId(record.get(alias.ID));
        mainTheme.setEventId(record.get(alias.EVENT_ID));
        mainTheme.setName(record.get(alias.NAME));
        mainTheme.setDescription(record.get(alias.DESCRIPTION));

        return mainTheme;
    }
}
